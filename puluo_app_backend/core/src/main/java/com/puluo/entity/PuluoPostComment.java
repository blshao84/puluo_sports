package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPostComment {

	String idComment();
	Date commentDate();
	Time commentTime();
	String content();
	String idPost();
	String from();
	String to();
}
