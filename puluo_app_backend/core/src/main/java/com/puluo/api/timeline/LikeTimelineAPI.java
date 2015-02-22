package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.LikeTimelineResult;


public class LikeTimelineAPI extends PuluoAPI<LikeTimelineResult> {

	public String timeline_uuid;

	public LikeTimelineAPI(String timeline_uuid) {
		super();
		this.timeline_uuid = timeline_uuid;
	}

	@Override
	public LikeTimelineResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}