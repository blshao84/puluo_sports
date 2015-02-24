package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DeleteTimelineCommentResult;


public class DeleteTimelineCommentAPI extends PuluoAPI<DeleteTimelineCommentResult> {

	public String comment_uuid;

	public DeleteTimelineCommentAPI(String comment_uuid) {
		super();
		this.comment_uuid = comment_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}