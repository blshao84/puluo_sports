package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoTimelineLike {
	
	public String userUUID();
	public String userName();
	public DateTime createdAt();
	
}
