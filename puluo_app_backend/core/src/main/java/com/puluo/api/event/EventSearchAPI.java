package com.puluo.api.event;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.SortDirection;
import com.puluo.util.TimeUtils;

public class EventSearchAPI extends PuluoAPI<PuluoDSI, EventSearchResult> {
	public static Log log = LogFactory.getLog(EventSearchAPI.class);
	public DateTime event_date;
	public String keyword;
	public String level;
	public EventSortType sort; 
	public SortDirection sort_direction;
	public Double latitude;
	public Double longitude;
	public Double range_from;

	public EventSearchAPI(DateTime event_date, String keyword, String level,
			EventSortType sort, SortDirection sort_direction, double latitude,
			double longitude) {
		this(event_date, keyword, level, sort, sort_direction, latitude,
				longitude, DaoApi.getInstance());
	}

	public EventSearchAPI(DateTime event_date, String keyword, String level,
			EventSortType sort, SortDirection sort_direction, double latitude,
			double longitude, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_date = event_date;
		this.keyword = keyword;
		this.level = level;
		this.sort = sort;
		this.sort_direction = sort_direction;
		this.latitude = latitude;
		this.longitude = longitude;
		//TODO: making this 0.0 ignores the location search
		this.range_from = 0.0; // FIXME: should be fixed!!! -BS
	}

	@Override
	public void execute() {
		log.info(String
				.format("开始根据Event Date:%s, Keyword:%s, Level:%s, Sort:%s, "
						+ "Sort Direction:%s, Latitude:%s, Longitude:%s, Range From:%s条件查找用户",
						TimeUtils.formatDate(event_date), keyword, level, sort,
						sort_direction, latitude.toString(), longitude.toString(), range_from.toString()));
		PuluoEventDao event_dao = dsi.eventDao();
		if (keyword.trim().isEmpty())
			keyword = null;
		List<PuluoEvent> events = event_dao.findEvents(event_date, keyword,
				level, sort, sort_direction, latitude, longitude, range_from);
		log.info(String.format("找到符合条件的%d个活动", events.size()));
		EventSearchResult result = new EventSearchResult();
		result.setSearchResult(events);
		rawResult = result;
	}
}
