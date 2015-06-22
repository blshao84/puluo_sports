package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoTimelineLike {
	
	public String likeUUID();	
	public String userUUID();
	public String timelineUUID();
	public String userName();
	public DateTime createdAt();
	public PuluoTimelinePost timeline();
	public PuluoUser user();
}
