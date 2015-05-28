package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoTimelinePostImpl implements PuluoTimelinePost {

	protected String uuid;
	protected String event_uuid;
	protected String owner_uuid;
	protected String content;
	protected List<String> images;
	protected DateTime created_at;

	public PuluoTimelinePostImpl(String uuid, String eventUUID, String ownerUUID,
			String timelineContent, DateTime creationTimestamp,
			DateTime upDateTimestamp) {
		super();
		this.uuid = uuid;
		this.event_uuid = eventUUID;
		this.owner_uuid = ownerUUID;
		this.content = timelineContent;
		this.created_at = creationTimestamp;
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
		return created_at;
	}

	// TODO: the following methods need using id to fetch information from other
	// tables
	@Override
	public PuluoUser owner() {
		return owner(DaoApi.getInstance());
	}
	
	public PuluoUser owner(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(owner_uuid);
	}
	
	@Override
	public PuluoEvent event() {
		return event(DaoApi.getInstance());
	}

	public PuluoEvent event(PuluoDSI dsi) {
		return dsi.eventDao().getEventByUUID(event_uuid);
	}

	@Override
	public List<PuluoTimelineLike> likes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoTimelineComment> comments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> imageURLs() {
		// TODO Auto-generated method stub
		return null;
	}

}
