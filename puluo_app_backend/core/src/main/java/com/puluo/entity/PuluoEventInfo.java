package com.puluo.entity;


public interface PuluoEventInfo {
	
	String eventInfoUUID();
	String name();
	String description(); 
	String coachName();
	String coachUuid();
	String thumbnail();
	String details();
	int duration(); 
	int level(); 
	int type(); 
	int likes();
	float rating(); 	
}
