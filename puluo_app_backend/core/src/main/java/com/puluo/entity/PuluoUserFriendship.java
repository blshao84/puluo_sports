package com.puluo.entity;

import java.util.List;

import com.puluo.entity.impl.PuluoFriendInfo;

public interface PuluoUserFriendship {
	public String fromUserUUID();
	public String toUserUUID();
	public List<PuluoFriendInfo> friendsOfFromUser();
	public List<PuluoFriendInfo> friendsOfToUser();
}
