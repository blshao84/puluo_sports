package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.DenyFriendResult;
import com.puluo.api.result.MessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class DenyFriendAPI extends PuluoAPI<PuluoDSI,DenyFriendResult> {
	public static Log log = LogFactory.getLog(DenyFriendAPI.class);
	private final String receiver;
	private final String requestor;
	
	public DenyFriendAPI(String receiver, String requestor){
		this(receiver,requestor, DaoApi.getInstance());
	}
	public DenyFriendAPI(String receiver, String requestor, PuluoDSI dsi) {
		this.dsi = dsi;
		this.receiver = receiver;
		this.requestor = requestor;
	}

	@Override
	public void execute() {
		log.info(String.format("用户:%s拒绝用户:%s提出的好友申请",receiver,requestor));
		PuluoFriendRequestDao friendRequestDao = dsi.friendRequestDao();
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		PuluoUserDao userdao = dsi.userDao();
		
		//先取得从requestor发送给receiver的status为pending的request
		List<PuluoFriendRequest> requests = friendRequestDao.getFriendRequestByUsers(requestor,receiver,FriendRequestStatus.Requested);
		if (requests.size()==0) {
			log.error(String.format("好友请求不存在"));
			this.error = ApiErrorResult.getError(38);
		} else {
			//如果存在多条从requestor发送给receiver的status为pending的request，全部置为denied状态
			for (PuluoFriendRequest tmp: requests) {
				friendRequestDao.updateRequestStatus(tmp.requestUUID(), FriendRequestStatus.Denied);
			}
			
			//如果存在多条从receiver发送给requestor的status为pending的request，全部置为denied状态
			for (PuluoFriendRequest tmp: friendRequestDao.getFriendRequestByUsers(receiver,requestor,FriendRequestStatus.Requested)) {
				friendRequestDao.updateRequestStatus(tmp.requestUUID(), FriendRequestStatus.Denied);
			}
			
			PuluoFriendRequest request = requests.get(0);
			
			PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
					String.format("用户:%s拒绝了您的好友申请",userdao.getByUUID(receiver).name()),
					DateTime.now(),PuluoMessageType.FriendRequest,request.requestUUID(),receiver,requestor);
			messagedao.saveMessage(message);

			List<MessageResult> messages_result =  new ArrayList<MessageResult>();
			for(int i=0;i<request.messages().size();i++) 
				messages_result.add(new MessageResult(request.messages().get(i).messageUUID(),
						request.messages().get(i).fromUser().name(),request.messages().get(i).toUser().name(),
						request.messages().get(i).fromUser().thumbnail(),request.messages().get(i).toUser().thumbnail(),
						request.messages().get(i).content(),TimeUtils.dateTime2Millis(request.messages().get(i).createdAt())));
			DenyFriendResult result = new DenyFriendResult(request.requestUUID(),request.requestStatus().name(),
					messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
			rawResult = result;
		}
	}
}
