package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.enumeration.FriendRequestStatus;

public interface PuluoFriendRequest {

	String requestUUID();
	FriendRequestStatus requestStatus();
	List<PuluoPrivateMessage> messages();
	PuluoUser fromUser();
	PuluoUser toUser();
	DateTime createdAt();
	DateTime updatedAt();
}
