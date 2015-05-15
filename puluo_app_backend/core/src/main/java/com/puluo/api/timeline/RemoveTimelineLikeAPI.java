package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.LikeTimelineResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class RemoveTimelineLikeAPI extends PuluoAPI<PuluoDSI,LikeTimelineResult> {
	public static Log log = LogFactory.getLog(RemoveTimelineLikeAPI.class);
	public String timeline_uuid;
	public String uuid;

	public RemoveTimelineLikeAPI(String timeline_uuid, String uuid){
		this(timeline_uuid, uuid, DaoApi.getInstance());
	}
	
	public RemoveTimelineLikeAPI(String timeline_uuid, String uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.timeline_uuid = timeline_uuid;
		this.uuid = uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始删除用户%s在时间线%s的like", uuid, timeline_uuid));
		PuluoTimelineLikeDao like_dao = dsi.postLikeDao();
		String status = like_dao.removeTimelineLike(timeline_uuid, null);
		if(status.equals("success")) {
			LikeTimelineResult like_result = new LikeTimelineResult(status);
			rawResult = like_result;
		} else {
			log.error(String.format("删除用户like失败"));
			this.error = ApiErrorResult.getError(25);
		}
	}
}