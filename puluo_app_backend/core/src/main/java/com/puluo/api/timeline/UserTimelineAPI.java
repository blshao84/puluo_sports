package com.puluo.api.timeline;

import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoTimelineDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.result.UserTimelineResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class UserTimelineAPI extends PuluoAPI<PuluoDSI,UserTimelineResult> {
	public static Log log = LogFactory.getLog(UserTimelineAPI.class);
	public String user_uuid;
	public String since_time;
	public int limit;
	public int offset;

	public UserTimelineAPI(String user_uuid, String since_time,int limit, int offset){
		this(user_uuid, since_time,limit,offset,DaoApi.getInstance());
	}
	
	public UserTimelineAPI(String user_uuid, String since_time,int limit,int offset, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
		this.since_time = since_time;
		this.limit = limit;
		this.offset = offset;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户%s的时间线(%s)",user_uuid,since_time));
		PuluoTimelineDao post_dao = dsi.postDao();
		List<PuluoTimelinePost> posts = post_dao.getUserTimeline(user_uuid,since_time,limit,offset);
		UserTimelineResult result = new UserTimelineResult();
		result.setTimelinePosts(posts);
		rawResult = result;
	}
}
