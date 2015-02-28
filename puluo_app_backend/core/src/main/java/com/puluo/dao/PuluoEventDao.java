package com.puluo.dao;

import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventPoster;


public interface PuluoEventDao {
	
	public boolean createTable();
	
	public PuluoEvent getEventDetails(String idevent);
	
	public PuluoEventPoster[] getEventMemoris(String idevent);
	
	public PuluoEvent[] findEvents(String name, String description, int max_count, String filter);
}
