package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPost {

	String idPost();
	String textContent();
	String imgContent();
	Date postDate();
	Time postTime();
	int type();
	String[] postCommentId();
}
