package com.puluo.api.message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListMessageSummaryResult;
import com.puluo.api.result.MessageSummaryResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class ListMessageSummaryAPI extends
		PuluoAPI<PuluoDSI, ListMessageSummaryResult> {
	public static Log log = LogFactory.getLog(ListMessageSummaryAPI.class);
	public final String user_uuid;
	public final int limit;
	public final int offset;

	public ListMessageSummaryAPI(String user_uuid, int limit, int offset) {
		this(user_uuid, limit, offset, DaoApi.getInstance());
	}

	public ListMessageSummaryAPI(String user_uuid, int limit, int offset,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	public void execute() {
		PuluoPrivateMessageDao msgDao = dsi.privateMessageDao();
		List<PuluoPrivateMessage> receivedMsg = msgDao
				.getReceivedMessageSummary(user_uuid);
		List<PuluoPrivateMessage> sentMsg = msgDao
				.getSentMessageSummary(user_uuid);
		TreeMap<String, TreeSet<PuluoPrivateMessage>> buffers = new TreeMap<String, TreeSet<PuluoPrivateMessage>>();
		addMessages(receivedMsg, buffers);
		addMessages(sentMsg, buffers);
		int i = 0;
		Iterator<String> keys = buffers.keySet().iterator();
		List<MessageSummaryResult> results = new ArrayList<MessageSummaryResult>();
		while (keys.hasNext()) {
			String key = keys.next();
			log.info(String.format("process user %s", key));
			if ((limit == 0) || (i >= offset && i < offset + limit)) {
				log.info(String.format("\toffset:%s,limit:%s,i=%s,add user %s", offset,limit,i,key));
				PuluoPrivateMessage msg = buffers.get(key).first();
				PuluoUser theOtherUser = null;
				if (user_uuid.equals(msg.fromUserUUID())) {
					theOtherUser = msg.toUser();
				} else
					theOtherUser = msg.fromUser();
				log.info(String
						.format("create messagey summary:toUser=%s,content=%s,createdAt=%s",
								theOtherUser.mobile(), msg.content(),
								TimeUtils.formatDate(msg.createdAt())));
				MessageSummaryResult res = new MessageSummaryResult(theOtherUser.firstName(),
						theOtherUser.lastName(), theOtherUser.userUUID(),
						theOtherUser.thumbnail(), msg.createdAt().getMillis(),
						msg.messageUUID(),msg.content());
				results.add(res);
			}
			i++;
		}
		this.rawResult = new ListMessageSummaryResult(results);
	}

	private void addMessages(List<PuluoPrivateMessage> messages,
			TreeMap<String, TreeSet<PuluoPrivateMessage>> buffers) {
		for (PuluoPrivateMessage msg : messages) {
			String theOtherUUID = null;
			if (msg.fromUserUUID().equals(user_uuid)) {
				theOtherUUID = msg.toUserUUID();
			} else
				theOtherUUID = msg.fromUserUUID();

			TreeSet<PuluoPrivateMessage> msgFromOneUser = null;
			if (buffers.containsKey(theOtherUUID)) {
				msgFromOneUser = buffers.get(theOtherUUID);
			} else {
				msgFromOneUser = new TreeSet<PuluoPrivateMessage>(
						new Comparator<PuluoPrivateMessage>() {

							@Override
							public int compare(PuluoPrivateMessage o1,
									PuluoPrivateMessage o2) {
								return o2.createdAt().compareTo(o1.createdAt());
							}
						});
			}
			msgFromOneUser.add(msg);
			buffers.put(theOtherUUID, msgFromOneUser);
		}
	}
}
