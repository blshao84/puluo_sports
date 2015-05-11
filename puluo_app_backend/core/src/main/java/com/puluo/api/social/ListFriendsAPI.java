package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoUserInfo;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.CommonListAPIResult;
import com.puluo.result.user.ListResultUserDetail;
import com.puluo.result.user.ListUserPublicInfoResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class ListFriendsAPI extends PuluoAPI<PuluoDSI, CommonListAPIResult> {
	public static Log log = LogFactory.getLog(ListFriendsAPI.class);
	private final String user_mobile_or_uuid;
	private final int limit;
	private final int offset;

	public ListFriendsAPI(String user_mobile_or_uuid, int limit, int offset) {
		this(user_mobile_or_uuid,limit,offset, DaoApi.getInstance());
	}

	public ListFriendsAPI(String user_mobile_or_uuid, int limit, int offset, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_mobile_or_uuid = user_mobile_or_uuid;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户:%s的好友列表", user_mobile_or_uuid));
		PuluoUserFriendshipDao friendshipdao = dsi.friendshipDao();
		if (user()!=null) {
			List<ListResultUserDetail> friend_detail = new ArrayList<ListResultUserDetail>();
			PuluoUserFriendship friendlist = friendshipdao.getFriendListByUUID(user().userUUID(),limit,offset);
			if (friendlist!=null) {
				List<PuluoUserInfo> friends = friendlist.friends();
				for (PuluoUserInfo friend: friends) {
					friend_detail.add(new ListResultUserDetail(friend.user_uuid,
							new ListUserPublicInfoResult(
									friend.first_name, 
									friend.last_name, 
									friend.user_email, 
									friend.user_mobile,
									friend.user_thumbnail,
									friend.user_saying)));
				}
			}
			CommonListAPIResult result = new CommonListAPIResult(friend_detail);
			rawResult = result;
		} else {
			log.error(String.format("用户%s不存在",user_mobile_or_uuid));
			this.error = ApiErrorResult.getError(17);
		}
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
