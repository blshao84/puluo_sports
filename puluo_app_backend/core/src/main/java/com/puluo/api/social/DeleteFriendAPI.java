package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoUserInfo;
import com.puluo.result.message.DeleteFriendResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class DeleteFriendAPI extends PuluoAPI<PuluoDSI,DeleteFriendResult> {
	public static Log log = LogFactory.getLog(DeleteFriendAPI.class);
	private final String fromUser;
	private final String toUser;
	
	public DeleteFriendAPI(String toUser, String fromUser){
		this(toUser,fromUser, DaoApi.getInstance());
	}
	
	public DeleteFriendAPI(String toUser, String fromUser, PuluoDSI dsi) {
		this.dsi = dsi;
		this.fromUser = fromUser;
		this.toUser = toUser;
	}

	@Override
	public void execute() {
		log.info(String.format("用户:%s和用户:%s解除好友关系",fromUser,toUser));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		List<String> newFriends = new ArrayList<String>();
		PuluoUserFriendship friendship = friendshipdao.deleteOneFriend(fromUser,toUser);
		for(PuluoUserInfo info:friendship.friends()){
			newFriends.add(info.user_uuid);
		}
		DeleteFriendResult result = new DeleteFriendResult(newFriends);
		rawResult = result;
	}
}
