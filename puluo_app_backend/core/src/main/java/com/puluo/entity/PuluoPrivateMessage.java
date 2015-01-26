package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPrivateMessage {

	String getTextContent(String idmessage);
	
	String getImgContent(String idmessage);
	
	String getDateTime(String idmessage);
	
	int getType(String idmessage);
	
	String[] findMessageId(String iduser, String idfriend, Date date, Time time);
}
