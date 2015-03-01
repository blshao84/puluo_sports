package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoPrivateMessage {

	String messageId();
	String textContent();
	String imgContent(); // TODO fix me
	DateTime msgTime();
	int type(); // TODO fix me
	int direction(); // TODO fix me
	String userId(); // TODO fix me
	String friendId(); // TODO fix me
	
	String fromUserId();
	String toUserid();
	String fromUserThumbnail();
	String toUserThumbnail();
}
