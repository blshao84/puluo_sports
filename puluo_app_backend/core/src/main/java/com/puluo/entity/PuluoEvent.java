package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoEvent {

	String idEvent();
	Date eventDate();
	Time eventTime();
	String name();
	int duration();
	String description();
	int level();
	float rating();
	int type();
	String idLocation();
	String[] eventPhotoId();
}
