package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import com.puluo.util.HasJSON;


public class EventSearchResult extends HasJSON {
	public List<EventSearchResultDetail> details;
	
	public EventSearchResult(List<EventSearchResultDetail> details) {
		super();
		this.details = details;
	}
	
	public static EventSearchResult dummy() {
		List<EventSearchResultDetail> details = new ArrayList<EventSearchResultDetail>();
		details.add(new EventSearchResultDetail(DateTime.parse("2014-02-18"), "yoga", "medium", "distance", "desc", "39.92889","116.38833", 10));
		return new EventSearchResult(details);
	}
}

class EventSearchResultDetail {
	public DateTime event_date;
	public String keyword;
	public String level;
	public String sort;
	public String sort_direction;
	public String latitude;
	public String longitude;
	public int range_from;
	
	public EventSearchResultDetail(DateTime event_date, String keyword, String level, String sort,
			String sort_direction, String latitude, String longitude, int range_from) {
		super();
		this.event_date = event_date;
		this.keyword = keyword;
		this.level = level;
		this.sort = sort;
		this.sort_direction = sort_direction;
		this.latitude = latitude;
		this.longitude = longitude;
		this.range_from = range_from;
	}
}
