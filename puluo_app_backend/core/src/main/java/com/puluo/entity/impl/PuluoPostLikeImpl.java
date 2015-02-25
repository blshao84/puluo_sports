package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoTimelineLike;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoPostLikeImpl implements PuluoTimelineLike {
	
	private String timeline_uuid;
	private String user_uuid;
	private String user_name;
	private DateTime creationTimestamp;

	@Override
	public String userUUID() {
		// TODO Auto-generated method stub
		return user_uuid;
	}

	@Override
	public String userName() {
		// TODO Auto-generated method stub
		return user_name;
	}

	@Override
	public DateTime createdAt() {
		// TODO Auto-generated method stub
		return creationTimestamp;
	}

	@Override
	public String timelineUUID() {
		// TODO Auto-generated method stub
		return timeline_uuid;
	}

	@Override
	public PuluoTimelinePost timeline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser user() {
		// TODO Auto-generated method stub
		return null;
	}

}
