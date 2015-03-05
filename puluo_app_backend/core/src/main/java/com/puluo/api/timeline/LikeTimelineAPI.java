package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.LikeTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class LikeTimelineAPI extends PuluoAPI<PuluoDSI,LikeTimelineResult> {

	public String timeline_uuid;

	public LikeTimelineAPI(String timeline_uuid){
		this(timeline_uuid, DaoApi.getInstance());
	}
	
	public LikeTimelineAPI(String timeline_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.timeline_uuid = timeline_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}