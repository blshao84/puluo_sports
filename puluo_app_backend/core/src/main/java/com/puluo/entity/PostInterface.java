package com.puluo.entity;

import java.sql.Time;
import java.sql.Date;


public interface PostInterface {

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
