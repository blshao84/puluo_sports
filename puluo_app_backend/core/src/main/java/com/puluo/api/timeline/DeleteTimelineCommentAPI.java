package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DeleteTimelineCommentResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class DeleteTimelineCommentAPI extends PuluoAPI<PuluoDSI,DeleteTimelineCommentResult> {

	public String comment_uuid;

	public DeleteTimelineCommentAPI(String comment_uuid){
		this(comment_uuid, DaoApi.getInstance());
	}
	public DeleteTimelineCommentAPI(String comment_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.comment_uuid = comment_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}