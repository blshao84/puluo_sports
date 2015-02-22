package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserTimelineResult;


public class UserTimelineAPI extends PuluoAPI<UserTimelineResult> {

	public String user_uuid;
	public String since_time;

	public UserTimelineAPI(String user_uuid, String since_time) {
		super();
		this.user_uuid = user_uuid;
		this.since_time = since_time;
	}

	@Override
	public UserTimelineResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
