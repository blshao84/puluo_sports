package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListFriendsResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;

public class ListFriendsAPI extends PuluoAPI<PuluoDSI, ListFriendsResult> {
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
		// TODO Auto-generated method stub

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
