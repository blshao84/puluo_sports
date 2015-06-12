package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.impl.PuluoEventAttendee;

public interface PuluoEvent {

	String eventUUID();
	DateTime eventTime();
	DateTime eventEndTime();
	String statusName();
	int registeredUsers();
	int capatcity();
	Double originalPrice();
	Double discount();
	Double discountedPrice();
	PuluoEventInfo eventInfo();
	PuluoEventLocation eventLocation();
	String eventInfoUUID();
	String eventLocationUUID();
	List<PuluoEventMemory> memory();
	int hottest();
	List<PuluoEventAttendee> attendees();
	boolean registered(String userUUID);
	Double price();
}
