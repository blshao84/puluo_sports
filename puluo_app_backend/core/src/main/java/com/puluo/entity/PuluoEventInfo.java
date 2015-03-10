package com.puluo.entity;

import java.util.List;


public interface PuluoEventInfo {
	
	String eventInfoUUID();
	String name();
	String description(); 
	String coachName();
	String coachUUID();
	String details();
	int duration(); 
	int level(); 
	int type(); 
	int likes();
	double rating(); 	
	List<PuluoEventPoster> poster();
}
