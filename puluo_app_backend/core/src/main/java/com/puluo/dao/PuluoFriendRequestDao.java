package com.puluo.dao;

import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;

public interface PuluoFriendRequestDao {
	public boolean createTable();
	public boolean saveNewRequest(String fromUser, String toUser);
	public boolean updateRequestStatus(String requestUUID,FriendRequestStatus newStatus);
	public PuluoFriendRequest findByUUID(String requestUUID);
}
