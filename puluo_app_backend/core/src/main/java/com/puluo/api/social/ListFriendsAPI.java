package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListFriendsPublicResult;
import com.puluo.api.result.ListFriendsResult;
import com.puluo.api.result.ListFriendsResultDetail;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class ListFriendsAPI extends PuluoAPI<PuluoDSI, ListFriendsResult> {
	public static Log log = LogFactory.getLog(ListFriendsAPI.class);
	private final String user_mobile_or_uuid;

	public ListFriendsAPI(String user_mobile_or_uuid) {
		this(user_mobile_or_uuid, DaoApi.getInstance());
	}

	public ListFriendsAPI(String user_mobile_or_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_mobile_or_uuid = user_mobile_or_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户:%s的好友列表", user_mobile_or_uuid));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		List<PuluoUserFriendship> friendlist = friendshipdao.getFriendListByUUID(user_mobile_or_uuid);
		List<ListFriendsResultDetail> friend_detail = new ArrayList<ListFriendsResultDetail>();
		for(int i=0;i<friendlist.size();i++) 
			friend_detail.add(new ListFriendsResultDetail(friendlist.get(i).userUUID(),
					new ListFriendsPublicResult(user().firstName(),user().lastName(),
					user().email(),user().mobile())));
		ListFriendsResult result = new ListFriendsResult(friend_detail);
		rawResult = result;
	}

	private PuluoUser user() {
		PuluoUser user = null;
		user = dsi.userDao().getByMobile(user_mobile_or_uuid);
		if (user != null)
			return user;
		else {
			user = dsi.userDao().getByUUID(user_mobile_or_uuid);
			return user;
		}
	}
}
