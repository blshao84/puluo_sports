package com.puluo.dao;


public interface PuluoPostCommentDao {
	
	public boolean createTable();
	
	public String commentUserTimeline(String timeline_uuid, String reply_to, String comment);
	
	public String removeCommentUserTimeline(String comment_uuid);
}
