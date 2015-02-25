package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPrivateMessage {

	String idMessage();
	String textContent();
	String imgContent(); // TODO fix me
	Date msgDate(); // TODO fix me
	Time msgTime();
	int type(); // TODO fix me
	int direction(); // TODO fix me
	String idUser(); // TODO fix me
	String idFriend(); // TODO fix me
	
	String fromUserId();
	String toUserid();
	String fromUserThumbnail();
	String toUserThumbnail();
}
