package com.puluo.api.message;

import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.message.SendMessageResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class SendMessageAPI extends PuluoAPI<PuluoDSI,SendMessageResult> {
	public static Log log = LogFactory.getLog(SendMessageAPI.class);
	public String from_uuid;
	public String to_uuid;
	public String content;
	public String content_type;
	
	public SendMessageAPI(String from_uuid, String to_uuid, String content, String content_type){
		this(from_uuid, to_uuid, content, content_type, DaoApi.getInstance());
	}
	
	public SendMessageAPI(String from_uuid, String to_uuid, String content, String content_type, PuluoDSI dsi) {
		this.dsi = dsi;
		this.from_uuid = from_uuid;
		this.to_uuid = to_uuid;
		this.content = content;
		this.content_type = content_type;
	}

	@Override
	public void execute() {
		log.info(String.format("开始发送用户:%s信息到用户:%s",from_uuid,to_uuid));
		PuluoUser fromUser = dsi.userDao().getByUUID(from_uuid);
		PuluoUser toUser = dsi.userDao().getByUUID(to_uuid);
		if (fromUser!=null && toUser!=null) {
			if (PuluoMessageType.TextMessage.name().equals(content_type)) {
				String message_id = UUID.randomUUID().toString();
				DateTime dt = DateTime.now();
				PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
				PuluoPrivateMessage message = new PuluoPrivateMessageImpl(message_id,content,
						dt,PuluoMessageType.valueOf(content_type),"",from_uuid,to_uuid);
				boolean save_status = messagedao.saveMessage(message);
				if(save_status) {
					String from_tn =fromUser.thumbnailURL();
					String to_tn = toUser.thumbnailURL();
					SendMessageResult result = new SendMessageResult(message_id,
							from_uuid,to_uuid,
							fromUser.firstName(),toUser.firstName(),
							fromUser.lastName(),toUser.lastName(),
							from_tn,to_tn,content,TimeUtils.dateTime2Millis(dt));
					rawResult = result;
				} else {
					log.error(String.format("用户%s发送消息到用户%s失败",from_uuid,message_id));
					this.error = ApiErrorResult.getError(31);
				}
			} else {
				log.error(String.format("用户%s发送消息失败,消息类型%s不存在",from_uuid,content_type));
				this.error = ApiErrorResult.getError(31);
			}
		} else {
			log.error(String.format("用户%s发送消息失败,用户%s不存在",from_uuid,to_uuid));
			this.error = ApiErrorResult.getError(31);
		}
	}
}
