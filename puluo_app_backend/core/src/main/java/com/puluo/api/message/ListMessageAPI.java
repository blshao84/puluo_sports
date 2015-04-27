package com.puluo.api.message;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListMessageResult;
import com.puluo.api.result.MessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class ListMessageAPI extends PuluoAPI<PuluoDSI,ListMessageResult> {
	public static Log log = LogFactory.getLog(ListMessageAPI.class);
	public String from_user_uuid;
	public String to_user_uuid;
	public DateTime since;
	
	public ListMessageAPI(String from_user_uuid, String to_user_uuid, DateTime since){
		this(from_user_uuid, to_user_uuid, since, DaoApi.getInstance());
	}
	
	public ListMessageAPI(String from_user_uuid, String to_user_uuid, DateTime since, PuluoDSI dsi) {
		this.dsi = dsi;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.since = since;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找从用户:%s到用户:%s的消息,since:%s",from_user_uuid,to_user_uuid,since));
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		List<PuluoPrivateMessage> messages = messagedao.getMessagesByUser(from_user_uuid,
				to_user_uuid,since,DateTime.now());
		List<MessageResult> messagelist =  new ArrayList<MessageResult>();
		for(int i=0;i<messages.size();i++) {
			PuluoPrivateMessage msg = messages.get(i);
			PuluoUser fromUser = msg.fromUser();
			PuluoUser toUser = msg.toUser();
			messagelist.add(new MessageResult(msg.messageUUID(),
					fromUser.userUUID(),toUser.userUUID(),
					fromUser.firstName(),toUser.firstName(),
					fromUser.lastName(),toUser.lastName(),
					fromUser.thumbnail(),toUser.thumbnail(),
					msg.content(),TimeUtils.dateTime2Millis(msg.createdAt())));
		}
		ListMessageResult result = new ListMessageResult(messagelist);
		rawResult = result;
	}
}
