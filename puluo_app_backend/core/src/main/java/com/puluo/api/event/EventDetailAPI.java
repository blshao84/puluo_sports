package com.puluo.api.event;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EventDetailResult;


public class EventDetailAPI extends PuluoAPI<EventDetailResult> {

	public String event_uuid;

	public EventDetailAPI(String event_uuid) {
		super();
		this.event_uuid = event_uuid;
	}

	@Override
	public EventDetailResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}