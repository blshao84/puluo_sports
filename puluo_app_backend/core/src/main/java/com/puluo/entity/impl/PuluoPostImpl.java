package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoPostImpl implements PuluoTimelinePost {

	protected String uuid;
	protected String event_uuid;
	protected String owner_uuid;
	protected String content;
	protected DateTime creation_time;
	protected DateTime update_time;

	public PuluoPostImpl(String uuid, String eventUUID, String ownerUUID,
			String timelineContent, DateTime creationTimestamp,
			DateTime upDateTimestamp) {
		super();
		this.uuid = uuid;
		this.event_uuid = eventUUID;
		this.owner_uuid = ownerUUID;
		this.content = timelineContent;
		this.creation_time = creationTimestamp;
		this.update_time = upDateTimestamp;
	}

	@Override
	public String timelineUUID() {
		return uuid;
	}

	@Override
	public String content() {
		return content;
	}

	@Override
	public DateTime createdAt() {
		return creation_time;
	}

	@Override
	public DateTime updatedAt() {
		return update_time;
	}

	// TODO: the following methods need using id to fetch information from other
	// tables
	@Override
	public PuluoUser owner() {
		return null;
	}
	
	@Override
	public PuluoEvent event() {
		return null;
	}

	@Override
	public List<PuluoTimelineLike> likes() {
		return null;
	}

	@Override
	public List<PuluoTimelineComment> comments() {
		return null;
	}

}
