package com.puluo.api.timeline;

import java.util.ArrayList;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPostDaoImpl;
import com.puluo.entity.PuluoTimelinePost;


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
		PuluoPostDaoImpl post_dao = new PuluoPostDaoImpl();
		ArrayList<PuluoTimelinePost> posts = post_dao.getUserTimeline(user_uuid, since_time);
		UserTimelineResult result = new UserTimelineResult();
		result.setTimelinePosts(posts);
		rawResult = result;
	}
}
