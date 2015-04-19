package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;

public interface PuluoEvent {

	String eventUUID();
	DateTime eventTime();
	String statusName();
	int registeredUsers();
	int capatcity();
	Double price();
	Double discount();
	Double discountedPrice();
	PuluoEventInfo eventInfo();
	PuluoEventLocation eventLocation();
	List<PuluoEventMemory> memory();
	int hottest();
}
