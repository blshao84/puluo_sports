package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoSession {
	
	public String  userUUID();
	
	public String  sessionID();
	
	public DateTime createdAt();
}
