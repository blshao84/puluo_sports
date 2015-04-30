package com.puluo.dao;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoEvent;
import com.puluo.enumeration.EventSortType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.enumeration.SortDirection;


public interface PuluoEventDao {
	
	public boolean createTable();
	
	public PuluoEvent getEventByUUID(String idevent);
	
	public List<PuluoEvent> findEvents(DateTime event_from_date,DateTime event_to_date, String keyword, PuluoEventLevel level,
			EventSortType sort, SortDirection sort_direction, double latitude, double longitude, double range_from, EventStatus es, PuluoEventCategory type);
	
	public boolean upsertEvent(PuluoEvent event);
	
	public boolean saveEvent(PuluoEvent event);
	
	public boolean updateEvent(PuluoEvent event);

	public List<PuluoEvent> findPopularEvent(int popularity);
}
