package com.puluo.dao;

import java.util.Date;
import java.util.List;
import com.puluo.entity.PuluoEvent;


public interface PuluoEventDao {
	
	public boolean createTable();
	
	public PuluoEvent getEventByUUID(String idevent);
	
	public List<PuluoEvent> findEvents(Date event_date, String keyword, String level, 
			String sort, String sort_direction, String latitude, String longitude, int range_from);
}
