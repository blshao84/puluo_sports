package com.puluo.dao;


public interface PuluoPostCommentDao {
	
	public boolean createTable();
	
	public String commentUserTimeline(String idpost);
	
	public String removeCommentUserTimeline(String idpost);
}
