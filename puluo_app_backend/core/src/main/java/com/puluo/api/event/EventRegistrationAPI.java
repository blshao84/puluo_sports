package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventRegistrationResult;


public class EventRegistrationAPI extends PuluoAPI<EventRegistrationResult> {

	public String event_uuid; 
	
	public EventRegistrationAPI(String event_uuid) {
		super();
		this.event_uuid = event_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}