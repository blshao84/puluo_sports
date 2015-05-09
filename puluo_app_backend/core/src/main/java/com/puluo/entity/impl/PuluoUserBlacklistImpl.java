package com.puluo.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserBlacklist;

public class PuluoUserBlacklistImpl implements PuluoUserBlacklist {

	private final String user_uuid;
	private final String[] banned_uuids;

	public PuluoUserBlacklistImpl(String user_uuid, String[] banned_uuids) {
		this.user_uuid = user_uuid;
		this.banned_uuids = banned_uuids;
	}

	@Override
	public String userUUID() {
		return user_uuid;
	}

	@Override
	public List<PuluoUserInfo> bannedUsers() {
		return bannedUsers(DaoApi.getInstance());
	}

	public List<PuluoUserInfo> bannedUsers(PuluoDSI dsi) {
		ArrayList<PuluoUserInfo> puluoFriendInfoList = new ArrayList<PuluoUserInfo>();
		PuluoUserDao puluoUserDao = dsi.userDao();
		PuluoUser puluoUser;
		String last_name;
		String first_name;
		String user_email;
		String user_mobile;
		String user_thumbnail;
		String user_saying;
		for (String banned_uuid : banned_uuids) {
			puluoUser = puluoUserDao.getByUUID(banned_uuid);
			last_name = puluoUser.lastName();
			first_name = puluoUser.firstName();
			user_email = puluoUser.email();
			user_mobile = puluoUser.mobile();
			user_thumbnail = puluoUser.thumbnailURL();
			user_saying = puluoUser.saying();
			puluoFriendInfoList.add(new PuluoUserInfo(banned_uuid, last_name,
					first_name, user_email, user_mobile, user_thumbnail,
					user_saying));
		}
		return puluoFriendInfoList;
	}

	@Override
	public List<String> bannedUUIDs() {
		List<String> ids = new ArrayList<String>();
		for(String banned_uuid : banned_uuids ){
			ids.add(banned_uuid);
		}
		return ids;
	}

}
