package com.puluo.dao;

import com.puluo.entity.PuluoUserFriendship;


public interface PuluoUserFriendshipDao {
	
	public boolean createTable();

	public PuluoUserFriendship getFriendListByUUID(String userUUID);
	
	public PuluoUserFriendship deleteOneFriend(String userUUID, String friendUUID);
	
	public PuluoUserFriendship addOneFriend(String userUUID, String friendUUID);
	
	public boolean isFriend(String oneUserUUID, String theOtherUUID);
}
