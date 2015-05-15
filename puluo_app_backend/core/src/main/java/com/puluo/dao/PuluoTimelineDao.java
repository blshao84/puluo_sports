package com.puluo.dao;

import java.util.List;
import com.puluo.entity.PuluoTimelinePost;


public interface PuluoTimelineDao {
	
	public boolean createTable();
	
	public List<PuluoTimelinePost> getUserTimeline(String user_uuid, String since_time);
}