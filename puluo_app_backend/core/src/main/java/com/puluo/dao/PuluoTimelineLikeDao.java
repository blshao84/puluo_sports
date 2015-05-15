package com.puluo.dao;


public interface PuluoTimelineLikeDao {
	
	public boolean createTable();
	
	public String saveTimelineLike(String timeline_uuid, String from_user_uuid);
	
	public String removeTimelineLike(String timeline_uuid, String from_user_uuid);
	
	public int getTotalLikes(String timeline_uuid);
}
