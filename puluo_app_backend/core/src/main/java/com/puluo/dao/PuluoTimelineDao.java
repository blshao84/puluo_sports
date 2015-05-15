package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoTimelinePost;


public interface PuluoTimelineDao {
	
	public boolean createTable();
	
	public List<PuluoTimelinePost> getUserTimeline(String user_uuid, String since_time, int limit, int offset);
	public boolean saveTimeline(PuluoTimelinePost timeline);
	public PuluoTimelinePost getByUUID(String uuid);
	public PuluoTimelinePost getByUserAndEvent(String user_uuid,String event_uuid);
}
