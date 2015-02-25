package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoPrivateMessage {

	String idMessage();
	String textContent();
	String imgContent();
	DateTime msgTime();
	int type();
	int direction();
	String idUser();
	String idFriend();
}
