package com.puluo.result.event;

import com.puluo.util.HasJSON;

public class EventRegistrationResult extends HasJSON{
	public String link;
	public String order_uuid;
	public boolean paid;
	
	public EventRegistrationResult(String link,
			String order_uuid,boolean paid) {
		super();
		this.link = link;
		this.order_uuid = order_uuid;
		this.paid = paid;
	}	
	
	public static EventRegistrationResult dummy() {
		return new EventRegistrationResult("https://alipay.com/xxxx", 
				"de305d54-75b4-431b-adb2-eb6b9e546015",true);
	}
}
