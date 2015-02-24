package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventMemoryResult;


public class EventMemoryAPI extends PuluoAPI<EventMemoryResult>{
	
	public String event_uuid;
	public int max_count;

	public EventMemoryAPI(String event_uuid, int max_count) {
		super();
		this.event_uuid = event_uuid;
		this.max_count = max_count;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
