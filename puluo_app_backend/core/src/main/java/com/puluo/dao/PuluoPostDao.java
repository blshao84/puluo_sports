package com.puluo.dao;

import java.util.ArrayList;
import com.puluo.entity.PuluoTimelinePost;


public interface PuluoPostDao {
	
	public boolean createTable();
	
	public ArrayList<PuluoTimelinePost> getUserTimeline(String user_uuid, String since_time);
}
