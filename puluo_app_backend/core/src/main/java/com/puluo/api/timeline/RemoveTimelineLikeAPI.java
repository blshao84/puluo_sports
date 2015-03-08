package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.LikeTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPostLikeDaoImpl;


public class RemoveTimelineLikeAPI extends PuluoAPI<PuluoDSI,LikeTimelineResult> {

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
		PuluoPostLikeDaoImpl like_dao = new PuluoPostLikeDaoImpl();
		String status = like_dao.likeUserTimeline(timeline_uuid, uuid);
		LikeTimelineResult like_result = new LikeTimelineResult(status);
		rawResult = like_result;
	}
}