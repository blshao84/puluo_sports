package com.puluo.dao;

import java.util.ArrayList;
import java.util.Date;
import com.puluo.entity.PuluoEvent;


public interface PuluoEventDao {
	
	public boolean createTable();
	
	public PuluoEvent getEventByUUID(String idevent);
	
	public ArrayList<PuluoEvent> findEvents(Date event_date, String keyword, String level, 
			String sort, String sort_direction, String latitude, String longitude, int range_from);
}
