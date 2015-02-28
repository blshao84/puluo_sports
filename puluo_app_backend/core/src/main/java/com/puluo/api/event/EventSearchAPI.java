package com.puluo.api.event;

import java.util.Date;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class EventSearchAPI extends PuluoAPI<PuluoDSI,EventSearchResult> {

	public Date event_date;
	public String keyword;
	public String sort;
	public String sort_direction;
	public String latitude;
	public String longitude;
	public int range_from;
	
	public EventSearchAPI(Date event_date, String keyword, String sort,
			String sort_direction, String latitude, String longitude,
			String categories, int range_from){
		this(event_date, keyword, sort, sort_direction, latitude, longitude, categories, range_from, new DaoApi());
	}
	
	public EventSearchAPI(Date event_date, String keyword, String sort,
			String sort_direction, String latitude, String longitude,
			String categories, int range_from, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_date = event_date;
		this.keyword = keyword;
		this.sort = sort;
		this.sort_direction = sort_direction;
		this.latitude = latitude;
		this.longitude = longitude;
		this.range_from = range_from;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
