package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoFriendRequest;
import com.puluo.enumeration.FriendRequestStatus;

public interface PuluoFriendRequestDao {
	
	public boolean createTable();
	
	public boolean saveNewRequest(String requestUUID, String fromUser, String toUser);
	
	public boolean updateRequestStatus(String requestUUID,FriendRequestStatus newStatus);
	
	public PuluoFriendRequest findByUUID(String requestUUID);
	
	public List<PuluoFriendRequest> getFriendRequestByUsers(String userUUID, String friendUUID,FriendRequestStatus status);
	
	public List<PuluoFriendRequest> getFriendRequestByUsers(String userUUID, String friendUUID);
	
	public List<PuluoFriendRequest> getPendingFriendRequestsByUserUUID(String userUUID,int limit, int offset);
}
