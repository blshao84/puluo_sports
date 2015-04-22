package com.puluo.entity;

import java.util.List;

import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;

public interface PuluoEventInfo {
	
	String eventInfoUUID();
	String name();
	String description(); 
	String coachName();
	String coachUUID();
	String coachThumbnail();
	String details();
	int duration(); 
	PuluoEventLevel level(); 
	PuluoEventCategory type(); 
	int likes();
	double rating(); 	
	List<PuluoEventPoster> poster();
}
