package com.puluo.api.message;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.SortDirection;
import com.puluo.result.message.ListMessageResult;
import com.puluo.result.message.MessageResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class ListMessageAPI extends PuluoAPI<PuluoDSI, ListMessageResult> {
	public static Log log = LogFactory.getLog(ListMessageAPI.class);
	public final String from_user_uuid;
	public final String to_user_uuid;
	public final DateTime since;
	public final int limit;
	public final int offset;

	public ListMessageAPI(String from_user_uuid, String to_user_uuid,
			DateTime since, int limit, int offset) {
		this(from_user_uuid, to_user_uuid, since, limit, offset, DaoApi
				.getInstance());
	}

	public ListMessageAPI(String from_user_uuid, String to_user_uuid,
			DateTime since, int limit, int offset, PuluoDSI dsi) {
		this.dsi = dsi;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.since = since;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找从用户:%s到用户:%s的消息,since:%s", from_user_uuid,
				to_user_uuid, since));
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		DateTime now = DateTime.now();
		List<PuluoPrivateMessage> messages = messagedao.getMessagesByUser(
				from_user_uuid, to_user_uuid, since, now,limit,offset,SortDirection.Asc);
		List<MessageResult> messagelist = new ArrayList<MessageResult>();
		for (int i = 0; i < messages.size(); i++) {
			PuluoPrivateMessage msg = messages.get(i);
			PuluoUser fromUser = msg.fromUser();
			PuluoUser toUser = msg.toUser();
			messagelist.add(new MessageResult(msg.messageUUID(), fromUser
					.userUUID(), toUser.userUUID(), fromUser.firstName(),
					toUser.firstName(), fromUser.lastName(), toUser.lastName(),
					fromUser.thumbnailURL(), toUser.thumbnailURL(), msg.content(),
					TimeUtils.dateTime2Millis(msg.createdAt())));
		}
		int total = messagedao.getMessagesCountByUser(from_user_uuid, to_user_uuid, since,now );
		ListMessageResult result = new ListMessageResult(messagelist,total);
		rawResult = result;
	}
}
