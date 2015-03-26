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
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class ListMessageAPI extends PuluoAPI<PuluoDSI,ListMessageResult> {
	public static Log log = LogFactory.getLog(ListMessageAPI.class);
	public String from_user_uuid;
	public String to_user_uuid;
	public String since;
	
	public ListMessageAPI(String from_user_uuid, String to_user_uuid, String since){
		this(from_user_uuid, to_user_uuid, since, DaoApi.getInstance());
	}
	
	public ListMessageAPI(String from_user_uuid, String to_user_uuid, String since, PuluoDSI dsi) {
		this.dsi = dsi;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.since = since;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找从用户:%s到用户:%s的消息,since:%s",from_user_uuid,to_user_uuid,since));
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		PuluoPrivateMessage[] messages = messagedao.getMessagesByUser(from_user_uuid,
				to_user_uuid,TimeUtils.parseDateTime(since),DateTime.now());
		List<MessageResult> messagelist =  new ArrayList<MessageResult>();
		for(int i=0;i<messages.length;i++) 
			messagelist.add(new MessageResult(messages[i].messageUUID(),messages[i].fromUser().userUUID(),
					messages[i].toUser().userUUID(),messages[i].fromUser().thumbnail(),messages[i].toUser().thumbnail(),
					messages[i].content(),TimeUtils.dateTime2Millis(messages[i].createdAt())));
		ListMessageResult result = new ListMessageResult(messagelist);
		rawResult = result;
	}
}
