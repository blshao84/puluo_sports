package com.puluo.entity;

import org.joda.time.DateTime;




public interface PuluoEvent {

	String idEvent();
	DateTime eventTime();
	String name();
	int duration();
	String description();
	int level();
	float rating();
	int type();
	String idLocation();
	String[] eventPhotoId();
}
