package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;



public interface PuluoTimelinePost {

	public String timelineUUID();
	public PuluoEvent event();
	public PuluoUser owner();
	public String content();
	public List<PuluoTimelineLike> likes();
	public List<PuluoTimelineComment> comments();
	public DateTime createdAt();
	public List<String> imageURLs();
}
