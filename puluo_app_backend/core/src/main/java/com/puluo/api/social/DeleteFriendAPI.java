package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DeleteFriendResult;
import com.puluo.api.result.PastMessagesResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class DeleteFriendAPI extends PuluoAPI<PuluoDSI,DeleteFriendResult> {
	public static Log log = LogFactory.getLog(DeleteFriendAPI.class);
	private final String receiver;
	private final String requestor;
	
	public DeleteFriendAPI(String receiver, String requestor){
		this(receiver,requestor, DaoApi.getInstance());
	}
	
	public DeleteFriendAPI(String receiver, String requestor, PuluoDSI dsi) {
		this.dsi = dsi;
		this.receiver = receiver;
		this.requestor = requestor;
	}

	@Override
	public void execute() {
		log.info(String.format("用户:%s和用户:%s解除好友关系",requestor,receiver));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		PuluoFriendRequestDao friendRequestDao = dsi.friendRequestDao();
		PuluoFriendRequest request = friendRequestDao.getFriendRequestByUsers(requestor,receiver);
		List<PuluoPrivateMessage> messages = request.messages();
		List<PastMessagesResult> msglist = new ArrayList<PastMessagesResult>();
		for(int i=0;i<messages.size();i++) {
			PastMessagesResult pastmsg = new PastMessagesResult(messages.get(i).messageUUID());
			msglist.add(pastmsg);
		}
//		friendshipdao.deleteOneFriend(requestor,receiver);
		friendshipdao.deleteOneFriend(receiver,requestor);
		DeleteFriendResult result = new DeleteFriendResult(msglist);
		rawResult = result;
	}
}
