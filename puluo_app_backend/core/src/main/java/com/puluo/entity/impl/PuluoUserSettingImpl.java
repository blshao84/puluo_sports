package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoUserSetting;

public class PuluoUserSettingImpl implements PuluoUserSetting {
	private final String user_uuid;
	private final boolean auto_friend;
	private final boolean timeline_public;
	private final boolean searchable;
	private final DateTime updated_at;

	public PuluoUserSettingImpl(String user_uuid, boolean auto_friend,
			boolean timeline_public, boolean searchable, DateTime updated_at) {
		super();
		this.user_uuid = user_uuid;
		this.auto_friend = auto_friend;
		this.timeline_public = timeline_public;
		this.searchable = searchable;
		this.updated_at = updated_at;
	}

	@Override
	public String userUUID() {
		return user_uuid;
	}

	@Override
	public boolean autoAddFriend() {
		return auto_friend;
	}

	@Override
	public boolean isTimelinePublic() {
		return timeline_public;
	}

	@Override
	public boolean isSearchable() {
		return searchable;
	}

}
