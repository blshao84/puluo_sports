package com.puluo.entity.impl;

import java.util.List;

import com.puluo.entity.PuluoFriendInfo;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUserFriendship;

public class PuluoUserFriendshipImpl implements PuluoUserFriendship{

	private final String user_uuid;
	private final List<PuluoFriendInfo> friends_info;
	
	
	public PuluoUserFriendshipImpl(String user_uuid,
			List<PuluoFriendInfo> friends_info) {
		super();
		this.user_uuid = user_uuid;
		this.friends_info = friends_info;
	}

	@Override
	public String userUUID() {
		// TODO Auto-generated method stub
		return user_uuid;
	}

	@Override
	public List<PuluoFriendInfo> friends() {
		// TODO Auto-generated method stub
		return friends_info;
	}

	@Override
	public PuluoFriendRequest request() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPrivateMessage[] pastMessages() {
		// TODO Auto-generated method stub
		return null;
	}

}


