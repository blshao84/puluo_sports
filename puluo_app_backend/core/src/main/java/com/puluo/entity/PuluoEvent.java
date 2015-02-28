package com.puluo.entity;

import org.joda.time.DateTime;




public interface PuluoEvent {

	String eventUUID();
	DateTime eventTime();
	String status();
	String[] images();
	String[] memories();
	int registeredUsers();
	int capatcity();
	Double price();
	Double discount();
	Double discountedPrice();
	PuluoEventInfo eventInfo();
	PuluoEventLocation eventLocation();
	
}
