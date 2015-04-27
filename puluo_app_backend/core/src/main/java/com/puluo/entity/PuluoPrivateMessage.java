package com.puluo.entity;

import org.joda.time.DateTime;

import com.puluo.enumeration.PuluoMessageType;


public interface PuluoPrivateMessage {

	String messageUUID();
	String content();
	DateTime createdAt();
	PuluoMessageType messageType();
	PuluoFriendRequest friendRequest();
	PuluoUser fromUser();
	PuluoUser toUser();
	String fromUserUUID();
	String toUserUUID();
	String requestUUID();
}
