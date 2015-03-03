package com.puluo.entity.impl;

import java.util.List;

import com.puluo.entity.PuluoUserFriendship;

public class PuluoUserFriendshipImpl implements PuluoUserFriendship {

	private final String from_uuid;
	private final String to_uuid;

	public PuluoUserFriendshipImpl(String from_uuid, String to_uuid) {
		super();
		this.from_uuid = from_uuid;
		this.to_uuid = to_uuid;
	}

	@Override
	public String fromUserUUID() {
		// TODO Auto-generated method stub
		return from_uuid;
	}

	@Override
	public String toUserUUID() {
		// TODO Auto-generated method stub
		return to_uuid;
	}

	@Override
	public List<com.puluo.entity.impl.PuluoFriendInfo> friendsOfFromUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<com.puluo.entity.impl.PuluoFriendInfo> friendsOfToUser() {
		// TODO Auto-generated method stub
		return null;
	}

}
