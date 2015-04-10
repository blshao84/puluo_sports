package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.MessageResult;
import com.puluo.api.result.RequestFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoFriendRequestImpl;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
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
		//FIXME:如果receiver已经向requester发送过请求怎么办？
		PuluoFriendRequest request = friendRequestDao.getFriendRequestByUsers(requestor,receiver);
		
		if(userdao.getByUUID(receiver)==null) {
			log.error(String.format("用户:%s不存在",receiver));
			this.error = ApiErrorResult.getError(32);
			return;
		}
		if(request==null) {
			request = new PuluoFriendRequestImpl(UUID.randomUUID().toString(),
					FriendRequestStatus.Requested,requestor,receiver,DateTime.now(),DateTime.now());
			friendRequestDao.saveNewRequest(request.requestUUID(), requestor, receiver);
			PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
					String.format("用户:%s向您提出好友申请",userdao.getByUUID(requestor).name()),
					DateTime.now(),PuluoMessageType.FriendRequest,request.requestUUID(),requestor,receiver);
			messagedao.saveMessage(message);
		} else if((request!=null) && request.requestStatus().toString().equals("Approved")) {
			log.error(String.format("用户:%s已经是您的好友",receiver));
			this.error = ApiErrorResult.getError(33);
		} else if((request!=null) && request.requestStatus().toString().equals("Requested")) {
			log.error(String.format("已经发送好友请求，正在等待对方批准"));
			this.error = ApiErrorResult.getError(34);
		} else if((request!=null) && request.requestStatus().toString().equals("Denied")) {
			friendRequestDao.updateRequestStatus(request.requestUUID(), FriendRequestStatus.Requested);
			PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
					String.format("用户:%s向您提出好友申请",userdao.getByUUID(requestor).name()),
					DateTime.now(),PuluoMessageType.FriendRequest,request.requestUUID(),requestor,receiver);
			messagedao.saveMessage(message);
		}	
		
		List<MessageResult> messages_result =  new ArrayList<MessageResult>();
		for(int i=0;i<request.messages().size();i++) 
			messages_result.add(new MessageResult(request.messages().get(i).messageUUID(),
					request.messages().get(i).fromUser().userUUID(),request.messages().get(i).toUser().userUUID(),
					request.messages().get(i).fromUser().thumbnail(),request.messages().get(i).toUser().thumbnail(),
					request.messages().get(i).content(),TimeUtils.dateTime2Millis(request.messages().get(i).createdAt())));
		RequestFriendResult result = new RequestFriendResult(request.requestUUID(),request.requestStatus().name(),
				messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
		rawResult = result;
	}
}
