package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DeleteFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoFriendInfo;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class DeleteFriendAPI extends PuluoAPI<PuluoDSI,DeleteFriendResult> {
	public static Log log = LogFactory.getLog(DeleteFriendAPI.class);
	private final String fromUser;
	private final String toUser;
	
	public DeleteFriendAPI(String fromUser, String toUser){
		this(fromUser,toUser, DaoApi.getInstance());
	}
	
	public DeleteFriendAPI(String fromUser, String toUser, PuluoDSI dsi) {
		this.dsi = dsi;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}

	@Override
	public void execute() {
		log.info(String.format("用户:%s和用户:%s解除好友关系",fromUser,toUser));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		PuluoFriendRequestDaoImpl friendRequestDao = (PuluoFriendRequestDaoImpl)dsi.friendRequestDao();
		PuluoFriendRequest request = friendRequestDao.getFriendRequestByUsers(fromUser,toUser,FriendRequestStatus.Approved);
		friendshipdao.deleteOneFriend(fromUser,toUser);
		if(request!=null){
			friendRequestDao.deleteByReqUUID(request.requestUUID());
		}else{
			log.warn(String.format("用户%s和%s之间的好友请求不存在", fromUser,toUser));
		}
		List<String> newFriends = new ArrayList<String>();
		PuluoUserFriendship fr = friendshipdao.getFriendListByUUID(fromUser);
		for(PuluoFriendInfo info:fr.friends()){
			newFriends.add(info.user_uuid);
		}
		DeleteFriendResult result = new DeleteFriendResult(newFriends);
		rawResult = result;
	}
}
