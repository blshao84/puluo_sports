package com.puluo.dao;


public interface PuluoTimelineLikeDao {
	
	public boolean createTable();
	
	public String likeUserTimeline(String timeline_uuid, String user_uuid);
	
	public String removeLikeUserTimeline(String timeline_uuid);
}
