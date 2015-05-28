package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoTimelineLikeImpl implements PuluoTimelineLike {
	
	private String timeline_uuid;
	private String user_uuid;
	private String user_name;
	private DateTime created_at;

	public PuluoTimelineLikeImpl(String timeline_uuid, String user_uuid,
			String user_name, DateTime created_at) {
		super();
		this.timeline_uuid = timeline_uuid;
		this.user_uuid = user_uuid;
		this.user_name = user_name;
		this.created_at = created_at;
	}

	@Override
	public String userUUID() {
		return user_uuid;
	}

	@Override
	public String userName() {
		return user_name;
	}

	@Override
	public DateTime createdAt() {
		return created_at;
	}

	@Override
	public String timelineUUID() {
		return timeline_uuid;
	}

	@Override
	public PuluoTimelinePost timeline() {
		return timeline(DaoApi.getInstance());
	}

	public PuluoTimelinePost timeline(PuluoDSI dsi) {
		return dsi.postDao().getByUUID(timeline_uuid);
	}

	@Override
	public PuluoUser user() {
		return user(DaoApi.getInstance());
	}

	public PuluoUser user(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(user_uuid);
	}
}
