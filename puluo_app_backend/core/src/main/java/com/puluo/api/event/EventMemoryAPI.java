package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventMemoryResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class EventMemoryAPI extends PuluoAPI<PuluoDSI,EventMemoryResult>{
	
	public String event_uuid;
	public int max_count;

	public EventMemoryAPI(String event_uuid, int max_count){
		this(event_uuid, max_count, DaoApi.getInstance());
	}
	public EventMemoryAPI(String event_uuid, int max_count, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
		this.max_count = max_count;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
