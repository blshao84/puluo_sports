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
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.message.ApproveFriendResult;
import com.puluo.result.message.MessageResult;
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
		
		//先取得从requestor发送给receiver的status为pending的request
		List<PuluoFriendRequest> requests = friendRequestDao.getFriendRequestByUsers(requestor,receiver,FriendRequestStatus.Requested);
		if (requests.size()==0) {
			log.error(String.format("好友请求不存在"));
			this.error = ApiErrorResult.getError(35);
		} else {
			friendshipdao.addOneFriend(requestor,receiver);
			
			//如果存在多条从requestor发送给receiver的status为pending的request，全部置为approved状态
			for (PuluoFriendRequest tmp: requests) {
				friendRequestDao.updateRequestStatus(tmp.requestUUID(), FriendRequestStatus.Approved);
			}
			
			//如果存在多条从receiver发送给requestor的status为pending的request，全部置为approved状态
			for (PuluoFriendRequest tmp: friendRequestDao.getFriendRequestByUsers(receiver,requestor,FriendRequestStatus.Requested)) {
				friendRequestDao.updateRequestStatus(tmp.requestUUID(), FriendRequestStatus.Approved);
			}
			
			PuluoFriendRequest request = requests.get(0);
			
			PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
					String.format("用户:%s接受您的好友申请!",userdao.getByUUID(receiver).name()),
					DateTime.now(),PuluoMessageType.FriendRequest,request.requestUUID(),receiver,requestor);
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
						messages.get(i).content(),TimeUtils.dateTime2Millis(messages.get(i).createdAt())));
			}
			ApproveFriendResult result = new ApproveFriendResult(request.requestUUID(),FriendRequestStatus.Approved.name(),
					messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(DateTime.now()));
			rawResult = result;
		}
	}
}
