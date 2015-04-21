package com.puluo.api.event;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.enumeration.EventSortType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.SortDirection;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class EventSearchAPI extends PuluoAPI<PuluoDSI, EventSearchResult> {
	public static Log log = LogFactory.getLog(EventSearchAPI.class);
	public DateTime event_from_date;
	public DateTime event_to_date;
	public String keyword;
	public String level;
	public EventSortType sort;
	public SortDirection sort_direction;
	public Double latitude;
	public Double longitude;
	public Double range_from = 5.0;
	public EventStatus status = EventStatus.Open;
	public List<PuluoEvent> searchedEvents;
	
	public EventSearchAPI(DateTime event_from_date, DateTime event_to_date,
			String keyword, String level, EventSortType sort,
			SortDirection sort_direction, double latitude, double longitude,
			double range_from, EventStatus status) {
		this(event_from_date, event_to_date, keyword, level, sort,
				sort_direction, latitude, longitude, range_from, status, DaoApi
						.getInstance());
	}

	public EventSearchAPI(DateTime event_from_date, DateTime event_to_date,
			String keyword, String level, EventSortType sort,
			SortDirection sort_direction, double latitude, double longitude,
			double range_from, EventStatus status, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_from_date = event_from_date;
		this.event_to_date = event_to_date;
		this.keyword = keyword;
		this.level = level;
		this.sort = sort;
		this.sort_direction = sort_direction;
		this.latitude = latitude;
		this.longitude = longitude;
		this.range_from = range_from;
		this.status = status;
	}

	@Override
	public void execute() {
		log.info(String
				.format("开始根据Event From_Date:%s,To_Date:%s,  Keyword:%s, Level:%s, Sort:%s, "
						+ "Sort Direction:%s, Latitude:%s, Longitude:%s, Range From:%s条件查找用户",
						TimeUtils.formatDate(event_from_date),
						TimeUtils.formatDate(event_to_date), keyword, level,
						sort, sort_direction, latitude.toString(),
						longitude.toString(), range_from.toString()));
		PuluoEventDao event_dao = dsi.eventDao();
		if (keyword.trim().isEmpty())
			keyword = null;
		searchedEvents = event_dao.findEvents(
				getTodayMidNight(event_from_date),
				getTomorrowMidNight(event_to_date), keyword,
				level, sort, sort_direction, latitude, longitude, range_from,
				status);
		log.info(String.format("找到符合条件的%d个活动", searchedEvents.size()));
		EventSearchResult result = new EventSearchResult();
		result.setSearchResult(searchedEvents);
		rawResult = result;
	}
	
	private DateTime getTodayMidNight(DateTime time){
		if(time==null) return null;
		long timeInstant = time.getMillis();
		long midNightInstnat = timeInstant - time.getMillisOfDay();
		return new DateTime(midNightInstnat);
	}
	
	private DateTime getTomorrowMidNight(DateTime time){
		if(time==null) return null;
		return getTodayMidNight(time.plusDays(1));
	}
}
