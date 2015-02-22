package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoUserFriendship;

public interface PuluoUserFriendshipDao {

	public List<PuluoUserFriendship> getFriendListByUUID(String userUUID);
	
	public List<PuluoUserFriendship> deleteOneFriend(String userUUID, String frendUUID);
	
	public List<PuluoUserFriendship> addOneFriend(String userUUID, String frendUUID);
}