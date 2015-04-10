package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.ApproveFriendResult;
import com.puluo.api.result.MessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class ApproveFriendAPI extends PuluoAPI<PuluoDSI,ApproveFriendResult> {
	public static Log log = LogFactory.getLog(ApproveFriendAPI.class);
	private final String receiver;
	private final String requestor;
	
	public ApproveFriendAPI(String receiver, String requestor){
		this(receiver,requestor, DaoApi.getInstance());
	}
	
	public ApproveFriendAPI(String receiver, String requestor, PuluoDSI dsi) {
		this.dsi = dsi;
		this.receiver = receiver;
		this.requestor = requestor;
	}

	@Override
	public void execute() {
		log.info(String.format("开始批准用户:%s向用户:%s提出的好友申请",requestor,receiver));
		PuluoFriendRequestDao friendRequestDao = dsi.friendRequestDao();
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		PuluoUserDao userdao = dsi.userDao();
		
		PuluoFriendRequest request = friendRequestDao.getFriendRequestByUsers(requestor,receiver,FriendRequestStatus.Requested);
		if(request==null) {
			log.error(String.format("好友请求不存在"));
			this.error = ApiErrorResult.getError(35);
		} 
		else if((request!=null) && request.requestStatus().toString().equals("Approved")) {
			log.error(String.format("好友请求已被通过"));
			this.error = ApiErrorResult.getError(36);
		}
		else if((request!=null) && request.requestStatus().toString().equals("Denied")) {
			log.error(String.format("好友请求已被拒绝"));
			this.error = ApiErrorResult.getError(37);
		}
		else if((request!=null) && request.requestStatus().toString().equals("Requested")) {
			//friendshipdao.addOneFriend(requestor,receiver);
			friendshipdao.addOneFriend(receiver,requestor);
			friendRequestDao.updateRequestStatus(request.requestUUID(), FriendRequestStatus.Approved);
			PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
					String.format("用户:%s接受您的好友申请!",userdao.getByUUID(receiver).name()),
					DateTime.now(),PuluoMessageType.FriendRequest,requestor,receiver,requestor);
			messagedao.saveMessage(message);
		}
		
		List<MessageResult> messages_result =  new ArrayList<MessageResult>();
		for(int i=0;i<request.messages().size();i++) 
			messages_result.add(new MessageResult(request.messages().get(i).messageUUID(),
					request.messages().get(i).fromUser().name(),request.messages().get(i).toUser().name(),
					request.messages().get(i).fromUser().thumbnail(),request.messages().get(i).toUser().thumbnail(),
					request.messages().get(i).content(),TimeUtils.dateTime2Millis(request.messages().get(i).createdAt())));
		ApproveFriendResult result = new ApproveFriendResult(request.requestUUID(),request.requestStatus().name(),
				messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
		rawResult = result;
	}
}
