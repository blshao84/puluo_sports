package com.puluo.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserFriendship;

public class PuluoUserFriendshipImpl implements PuluoUserFriendship {

	private final String user_uuid;
	private final String[] friend_uuids;
	private PuluoDSI dsi;

	public PuluoUserFriendshipImpl(String user_uuid, String[] friend_uuids) {
		this(user_uuid, friend_uuids, DaoApi.getInstance());
	}

	public PuluoUserFriendshipImpl(String user_uuid, String[] friend_uuids,
			PuluoDSI dsi) {
		super();
		this.user_uuid = user_uuid;
		this.friend_uuids = friend_uuids;
		this.dsi = dsi;
	}

	@Override
	public void setDSI(PuluoDSI dsi) {
		this.dsi = dsi;
	}

	@Override
	public String userUUID() {
		return user_uuid;
	}

	@Override
	public List<PuluoFriendInfo> friends() {
		ArrayList<PuluoFriendInfo> puluoFriendInfoList = new ArrayList<PuluoFriendInfo>();
		PuluoUserDao puluoUserDao = dsi.userDao();
		PuluoUser puluoUser;
		String last_name;
		String first_name;
		String user_email;
		String user_mobile;
		String user_thumbnail;
		String user_saying;
		for (String friend_uuid : friend_uuids) {
			puluoUser = puluoUserDao.getByUUID(friend_uuid);
			last_name = puluoUser.lastName();
			first_name = puluoUser.firstName();
			user_email = puluoUser.email();
			user_mobile = puluoUser.mobile();
			user_thumbnail = puluoUser.thumbnail();
			user_saying = puluoUser.saying();
			puluoFriendInfoList.add(new PuluoFriendInfo(friend_uuid, last_name,
					first_name, user_email, user_mobile, user_thumbnail,
					user_saying));
		}
		return puluoFriendInfoList;
	}

}
