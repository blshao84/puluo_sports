package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoTimelineLike;


public interface PuluoTimelineLikeDao {
	
	public boolean createTable();
	
	public String saveTimelineLike(String timeline_uuid, String from_user_uuid);
	
	public String removeTimelineLike(String timeline_uuid, String from_user_uuid);
	
	public List<PuluoTimelineLike> getTotalLikes(String timeline_uuid);
}
