package com.puluo.api.timeline;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class UserTimelineAPI extends PuluoAPI<PuluoDSI,UserTimelineResult> {
	public static Log log = LogFactory.getLog(UserTimelineAPI.class);
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
		log.info(String.format("开始查找用户%s的时间线(%s)",user_uuid,since_time));
		PuluoPostDao post_dao = dsi.postDao();
		List<PuluoTimelinePost> posts = post_dao.getUserTimeline(user_uuid,since_time);
		UserTimelineResult result = new UserTimelineResult();
		result.setTimelinePosts(posts);
		rawResult = result;
	}
}
