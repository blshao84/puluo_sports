package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventDetailResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class EventDetailAPI extends PuluoAPI<PuluoDSI,EventDetailResult> {

	public String event_uuid;
	public EventDetailAPI(String event_uuid){
		this(event_uuid, DaoApi.getInstance());
	}
	public EventDetailAPI(String event_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}