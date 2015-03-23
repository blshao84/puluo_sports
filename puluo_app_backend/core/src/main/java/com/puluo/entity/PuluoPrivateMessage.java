package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoPrivateMessage {

	String messageUUID();
	String content();
	DateTime createdAt();
	PuluoMessageType messageType();
	PuluoFriendRequest friendRequest();
	PuluoUser fromUser();
	PuluoUser toUser();
}
