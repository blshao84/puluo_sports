package com.puluo.dao;

import java.util.List;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoUserFriendship;


public interface PuluoUserFriendshipDao {
	
	public boolean createTable();

	public List<PuluoUserFriendship> getFriendListByUUID(String userUUID);
	
	public List<PuluoUserFriendship> deleteOneFriend(String userUUID, String friendUUID);
	
	public List<PuluoUserFriendship> addOneFriend(String userUUID, String friendUUID);
	
	public PuluoFriendRequest getFriendRequestByUsers(String userUUID, String friendUUID);
	
	public String updateFriendshipStatus(PuluoFriendRequest request, FriendRequestStatus status);
}
