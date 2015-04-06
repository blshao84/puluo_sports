package com.puluo.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.EventStatus;
import com.puluo.api.event.EventSortType;
import com.puluo.entity.PuluoEvent;
import com.puluo.util.SortDirection;


public interface PuluoEventDao {
	
	public boolean createTable();
	
	public PuluoEvent getEventByUUID(String idevent);
	
	public List<PuluoEvent> findEvents(DateTime event_date, String keyword, String level,
			EventSortType sort, SortDirection sort_direction, double latitude, double longitude, double range_from, EventStatus es);
	
	public boolean upsertEvent(PuluoEvent event);
	
	public boolean saveEvent(PuluoEvent event);
	
	public boolean updateEvent(PuluoEvent event);
}
