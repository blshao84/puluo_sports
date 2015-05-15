package com.puluo.dao;


public interface PuluoTimelineCommentDao {
	
	public boolean createTable();
	
	public String saveTimelineComment(String timeline_uuid, String from_user_uuid, String comment);
	
	public String removeCommentUserTimeline(String comment_uuid);
}
