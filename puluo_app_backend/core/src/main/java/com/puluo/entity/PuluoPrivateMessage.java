package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPrivateMessage {

	String idMessage();
	String textContent();
	String imgContent();
	Date msgDate();
	Time msgTime();
	int type();
	int direction();
	String idUser();
	String idFriend();
}
