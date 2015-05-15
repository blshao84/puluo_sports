package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.LikeTimelineResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class LikeTimelineAPI extends PuluoAPI<PuluoDSI,LikeTimelineResult> {
	public static Log log = LogFactory.getLog(LikeTimelineAPI.class);
	public String timeline_uuid;
	public String uuid;

	public LikeTimelineAPI(String timeline_uuid, String uuid){
		this(timeline_uuid, uuid, DaoApi.getInstance());
	}
	
	public LikeTimelineAPI(String timeline_uuid, String uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.timeline_uuid = timeline_uuid;
		this.uuid = uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始添加用户%s时间线%s的like"), uuid, timeline_uuid);
		PuluoTimelineLikeDao like_dao = dsi.postLikeDao();
		String status = like_dao.saveTimelineLike(timeline_uuid, uuid);
		if(status.equals("success")) {
			LikeTimelineResult like_result = new LikeTimelineResult(status);
			rawResult = like_result;
		} else {
			log.error(String.format("添加用户like失败"));
			this.error = ApiErrorResult.getError(24);
		}
	}
}