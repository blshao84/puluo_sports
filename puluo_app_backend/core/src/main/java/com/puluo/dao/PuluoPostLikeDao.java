package com.puluo.dao;


public interface PuluoPostLikeDao {
	
	public boolean createTable();
	
	public String likeUserTimeline(String idpost);
	
	public String removeLikeUserTimeline(String idpost);
}
