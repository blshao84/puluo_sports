package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoEvent {

	String idEvent();
	Date eventDate(); // TODO fix me
	Time eventTime();
	String name();
	int duration(); // TODO fix me
	String description(); // TODO fix me
	int level(); // TODO fix me
	float rating(); // TODO fix me
	int type(); // TODO fix me
	String idLocation(); // TODO fix me
	String[] eventPhotoId(); // TODO fix me
	
	String status();
	String address();
	String city();
	String phone();
	String coachName();
	String coachUuid();
	String thumbnail();
	int registeredUsers();
	int capatcity();
	int likes();
	PuluoEventGeoLocation geoLocation();
	String details();
	String[] images();
	
	String[] memories();
}
