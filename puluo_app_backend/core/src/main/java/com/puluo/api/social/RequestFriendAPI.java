package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.MessageResult;
import com.puluo.api.result.RequestFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
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
import org.joda.time.DateTime;


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
		log.info(String.format("用户:%s向用户:%s提出的好友申请", requestor, receiver));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		PuluoPrivateMessageDao messagedao = dsi.privateMessageDao();
		PuluoUserDao userdao = dsi.userDao();
		PuluoFriendRequest request;
		if(friendshipdao.getFriendRequestByUsers(requestor,receiver).equals(null))
			request = new PuluoFriendRequestImpl(UUID.randomUUID().toString(),
					FriendRequestStatus.Requested,requestor,receiver,DateTime.now(),DateTime.now());
		else
			request = friendshipdao.getFriendRequestByUsers(requestor,receiver);
		PuluoPrivateMessage message = new PuluoPrivateMessageImpl(UUID.randomUUID().toString(),
				String.format("用户:%s向您提出好友申请",userdao.getByUUID(requestor).name()),
				DateTime.now(),PuluoMessageType.FriendRequest,requestor,requestor,receiver);
		//messagedao.sendMessage(message);
		List<MessageResult> messages_result =  new ArrayList<MessageResult>();
		for(int i=0;i<request.messages().size();i++) 
			messages_result.add(new MessageResult(request.messages().get(i).messageUUID(),
					request.messages().get(i).fromUser().name(),request.messages().get(i).toUser().name(),
					request.messages().get(i).fromUser().thumbnail(),request.messages().get(i).toUser().thumbnail(),
					request.messages().get(i).content(),TimeUtils.dateTime2Millis(request.messages().get(i).createdAt())));
		RequestFriendResult result = new RequestFriendResult(request.requestUUID(),request.requestStatus().name(),
				messages_result,TimeUtils.dateTime2Millis(request.createdAt()),TimeUtils.dateTime2Millis(request.updatedAt()));
		rawResult = result;
	}
}
