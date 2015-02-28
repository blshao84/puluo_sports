package com.puluo.dao;

import com.puluo.entity.PuluoTimelinePost;


public interface PuluoPostDao {
	
	public boolean createTable();
	
	public PuluoTimelinePost[] getUserTimeline(String userUUID);
}
