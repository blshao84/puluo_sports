package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPostComment;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;


public class PuluoPostImpl implements PuluoTimelinePost {
	
	protected String uuid;
	protected String eventUUID;
	protected String timelineContent;
	protected DateTime creationTimestamp;
	protected DateTime upDateTimestamp;
	
	
	public PuluoPostImpl(String uuid, String eventUUID, String timelineContent,
			DateTime creationTimestamp, DateTime upDateTimestamp) {
		super();
		this.uuid = uuid;
		this.eventUUID = eventUUID;
		this.timelineContent = timelineContent;
		this.creationTimestamp = creationTimestamp;
		this.upDateTimestamp = upDateTimestamp;
	}

	@Override
	public String timelineUUID() {
		return uuid;
	}
	

	@Override
	public String content() {
		return timelineContent;
	}


	@Override
	public DateTime createdAt() {
		return creationTimestamp;
	}

	@Override
	public DateTime updatedAt() {
		return upDateTimestamp;
	}
	
	//TODO: the following methods need using id to fetch information from other tables
	@Override
	public PuluoEvent event() {
		return null;
	}

	@Override
	public List<PuluoTimelineLike> likes() {
		return null;
	}

	@Override
	public List<PuluoPostComment> comments() {
		return null;
	}

}
