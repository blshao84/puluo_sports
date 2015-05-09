package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoUser;
import com.puluo.result.message.SingleFriendRequestResult;
import com.puluo.result.user.ListFriendRequestsResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class ListFriendRequestsAPI extends PuluoAPI<PuluoDSI, ListFriendRequestsResult> {
	public static Log log = LogFactory.getLog(ListFriendRequestsAPI.class);
	private final String user_uuid;

	public ListFriendRequestsAPI(String user_uuid) {
		this(user_uuid, DaoApi.getInstance());
	}

	public ListFriendRequestsAPI(String user_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户:%s的pending friend request列表", user_uuid));
		PuluoFriendRequestDao reqDao = dsi.friendRequestDao();
		List<PuluoFriendRequest> requests = reqDao.getPendingFriendRequestsByUserUUID(user_uuid);
		List<SingleFriendRequestResult> results = new ArrayList<SingleFriendRequestResult>();
		for(PuluoFriendRequest r:requests){
			PuluoUser fromUser = r.fromUser();
			PuluoUser toUser = r.toUser();
			results.add(new SingleFriendRequestResult(
					r.requestUUID(),
					fromUser.userUUID(),
					fromUser.firstName(),
					fromUser.lastName(),
					fromUser.thumbnailURL(),
					toUser.userUUID(),
					toUser.firstName(),
					toUser.lastName(),
					toUser.thumbnailURL(),
					r.createdAt().getMillis(),
					r.createdAt().getMillis()));
		}
		this.rawResult = new ListFriendRequestsResult(results);
		
	}
}
