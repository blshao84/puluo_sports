package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoPrivateMessage {

	String idMessage();
	String textContent();
	String imgContent(); // TODO fix me
	DateTime msgTime();
	int type(); // TODO fix me
	int direction(); // TODO fix me
	String idUser(); // TODO fix me
	String idFriend(); // TODO fix me
	
	String fromUserId();
	String toUserid();
	String fromUserThumbnail();
	String toUserThumbnail();
}
