package com.puluo.api.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;

public class EventRegistrationResult extends HasJSON{
	public String link;
	public String order_uuid;
	
	public EventRegistrationResult(String event_uuid, String link,
			String order_uuid) {
		super();
		this.link = link;
		this.order_uuid = order_uuid;
	}	
	
	public static EventRegistrationResult dummy() {
		return new EventRegistrationResult("de305d54-75b4-431b-adb2-eb6b9e546013","https://alipay.com/xxxx", 
				"de305d54-75b4-431b-adb2-eb6b9e546015");
	}
}
