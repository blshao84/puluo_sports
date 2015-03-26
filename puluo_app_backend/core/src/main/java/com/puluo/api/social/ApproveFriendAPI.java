package com.puluo.api.social;

import java.util.List;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApproveFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


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
		/*log.info(String.format("开始批准用户:%s向用户:%s提出的好友申请", requestor, receiver));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		List<PuluoUserFriendship> friendlist_receiver = friendshipdao.addOneFriend(receiver, requestor);
		List<PuluoUserFriendship> friendlist_requestor = friendshipdao.addOneFriend(requestor, receiver);*/
	}

}
