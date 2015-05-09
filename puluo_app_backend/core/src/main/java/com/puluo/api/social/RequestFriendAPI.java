package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoFriendRequestImpl;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.message.MessageResult;
import com.puluo.result.message.RequestFriendResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class RequestFriendAPI extends PuluoAPI<PuluoDSI, RequestFriendResult> {
	public static Log log = LogFactory.getLog(RequestFriendAPI.class);
	private final String receiver;
	private final String requestor;

	public RequestFriendAPI(String receiver, String requestor) {
		this(receiver, requestor, DaoApi.getInstance());
	}

	public RequestFriendAPI(String receiver, String requestor,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.receiver = receiver;
		this.requestor = requestor;
	}

	@Override
	public void execute() {
		log.info(String.format("用户:%s向用户:%s提出的好友申请",requestor,receiver));
		PuluoFriendRequestDao friendRequestDao = dsi.friendRequestDao();
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		PuluoUserDao userdao = dsi.userDao();
		PuluoUserFriendshipDao friendshipDao = dsi.friendshipDao();

		if(userdao.getByUUID(receiver)==null) {
			log.error(String.format("用户:%s不存在",receiver));
			this.error = ApiErrorResult.getError(32);
			return;
		}
		
		//检查requestor和receiver之间是否已有好友关系
		boolean isFriend = friendshipDao.isFriend(requestor, receiver);
		
		if (isFriend) {
			log.error(String.format("用户:%s已经是您的好友",receiver));
			this.error = ApiErrorResult.getError(33);
			return;
		} else {
			//保证在两个用户之间只有一个pending状态的request，虽然从理论上还是有可能多条
			//先取得从receiver发送给requestor的status为pending的request
			List<PuluoFriendRequest> requestFromReceiver = friendRequestDao.getFriendRequestByUsers(receiver,requestor,FriendRequestStatus.Requested);
			if (requestFromReceiver.size()>0) {
				log.error(String.format("对方已经向你发送过好友请求，正在等待你的批准"));
				this.error = ApiErrorResult.getError(42);
				return;
			}
			//先取得从requestor发送给receiver的status为pending的request
			List<PuluoFriendRequest> requestFromRequestor = friendRequestDao.getFriendRequestByUsers(requestor,receiver,FriendRequestStatus.Requested);
			if (requestFromRequestor.size()>0) {
				log.error(String.format("你已经向对方发送过好友请求，正在等待对方批准"));
				this.error = ApiErrorResult.getError(34);
				return;
			} else {
				PuluoFriendRequest request = new PuluoFriendRequestImpl(UUID.randomUUID().toString(),
						FriendRequestStatus.Requested, requestor, receiver,
						DateTime.now(), DateTime.now());
				friendRequestDao.saveNewRequest(request.requestUUID(), requestor, receiver);
				PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
						String.format("用户:%s向您提出好友申请",userdao.getByUUID(requestor).name()),
						DateTime.now(),PuluoMessageType.FriendRequest,request.requestUUID(),requestor,receiver);
				messagedao.saveMessage(message);
				
				List<MessageResult> messages_result =  new ArrayList<MessageResult>();
				List<PuluoPrivateMessage> messages = request.messages();
				for(int i=0;i<messages.size();i++) {
					PuluoPrivateMessage msg = messages.get(i);
					PuluoUser fromUser = msg.fromUser();
					PuluoUser toUser = msg.toUser();
					messages_result.add(new MessageResult(msg.messageUUID(),
							fromUser.userUUID(),toUser.userUUID(),
							fromUser.firstName(),toUser.firstName(),
							fromUser.lastName(),toUser.lastName(),
							fromUser.thumbnailURL(),toUser.thumbnailURL(),
							msg.content(),TimeUtils.dateTime2Millis(msg.createdAt())));
				}
				RequestFriendResult result = new RequestFriendResult(request.requestUUID(),request.requestStatus().name(),
						messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
				rawResult = result;
			}
		}
	}
}
