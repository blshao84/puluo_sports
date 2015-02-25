package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;



public interface PuluoTimelinePost {

	public String timelineUUID();
	public PuluoEvent event();
	public String content();
	public List<PuluoTimelineLike> likes();
	public List<PuluoPostComment> comments();
	public DateTime createdAt();
	public DateTime updatedAt();
}
