package com.puluo.api.event;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.EventStatus;
import com.puluo.entity.PuluoEvent;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;

public class EventSearchAPI extends PuluoAPI<PuluoDSI, EventSearchResult> {
	public static Log log = LogFactory.getLog(EventSearchAPI.class);
	public DateTime event_date;
	public String keyword;
	public String level;
	public String sort; // Luke 2015-03-21
						// 只提供时间，两点间举例，折后价格的排序；可穿入的值分别为"time"，"distance"，"price"
	public String sort_direction;
	public double latitude;
	public double longitude;
	public double range_from;
	public EventStatus status; // Luke 2015-04-06 EventDao的findEvent应该支持status

	public EventSearchAPI(DateTime event_date, String keyword, String level,
			String sort, String sort_direction, double latitude,
			double longitude, String status) {
		this(event_date, keyword, level, sort, sort_direction, latitude,
				longitude, status, DaoApi.getInstance());
	}

	public EventSearchAPI(DateTime event_date, String keyword, String level,
			String sort, String sort_direction, double latitude,
			double longitude, String status, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_date = event_date;
		this.keyword = keyword;
		this.level = level;
		this.sort = sort;
		this.sort_direction = sort_direction;
		this.latitude = latitude;
		this.longitude = longitude;
		this.range_from = 1.0; // FIXME: should be fixed!!! -BS
		if (Strs.isEmpty(status)) {
			this.status = null;
		} else {
			try {
				this.status = EventStatus.valueOf(status);
			} catch (Exception e) {
				this.status = null;
			}
		}
	}

	@Override
	public void execute() {
		log.info(String
				.format("开始根据Event Date:%s, Keyword:%s, Level:%s, Sort:%s, Status:%s"
						+ "Sort Direction:%s, Latitude:%s, Longitude:%s, Range From:%d条件查找用户",
						TimeUtils.formatDate(event_date), keyword, level, sort, status!=null ? status.name() : "",
						sort_direction, latitude, longitude, range_from));
		PuluoEventDao event_dao = dsi.eventDao();
		if (keyword.trim().isEmpty())
			keyword = null;
		List<PuluoEvent> events = event_dao.findEvents(event_date, keyword,
				level, sort, sort_direction, latitude, longitude, range_from, status);
		log.info(String.format("找到符合条件的%d个活动", events.size()));
		EventSearchResult result = new EventSearchResult();
		result.setSearchResult(events);
		rawResult = result;
	}
}
