package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventRegistrationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class EventRegistrationAPI extends PuluoAPI<PuluoDSI,EventRegistrationResult> {

	public String event_uuid; 
	public EventRegistrationAPI(String event_uuid){
		this(event_uuid, DaoApi.getInstance());
	}
	public EventRegistrationAPI(String event_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.event_uuid = event_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}