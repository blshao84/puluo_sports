package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPost {

	String getTextContent(String idpost);
	
	String getImgContent(String idpost);
	
	String getDateTime(String idpost);
	
	String getLocation(String idpost);
	
	String[] getCommentId(String idpost);
	
	String getFrom(String idcomment);
	
	String getTo(String idcomment);
	
	String getComment(String comment);
	
	String[] findPostId(String iduser, Date date, Time time);
	
}
