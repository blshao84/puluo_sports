package com.puluo.entity.impl;

import java.util.List;

import com.puluo.entity.PuluoFriendInfo;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUserFriendship;

public class PuluoUserFriendshipImpl implements PuluoUserFriendship{

	private final String user_uuid;
	private final String[] friend_uuids;
	
	
	

	public PuluoUserFriendshipImpl(String user_uuid, String[] friend_uuids) {
		super();
		this.user_uuid = user_uuid;
		this.friend_uuids = friend_uuids;
	}

	@Override
	public String userUUID() {
		// TODO Auto-generated method stub
		return user_uuid;
	}

	@Override
	public List<PuluoFriendInfo> friends() {
		// TODO Auto-generated method stub
		return null;
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


