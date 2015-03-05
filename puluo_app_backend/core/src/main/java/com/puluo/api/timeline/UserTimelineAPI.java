package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class UserTimelineAPI extends PuluoAPI<PuluoDSI,UserTimelineResult> {

	public String user_uuid;
	public String since_time;

	public UserTimelineAPI(String user_uuid, String since_time){
		this(user_uuid, since_time, DaoApi.getInstance());
	}
	public UserTimelineAPI(String user_uuid, String since_time, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
		this.since_time = since_time;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
