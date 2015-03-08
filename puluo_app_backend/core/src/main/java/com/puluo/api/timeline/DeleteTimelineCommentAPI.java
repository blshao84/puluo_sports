package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.CommentTimelineResult;
import com.puluo.api.result.DeleteTimelineCommentResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPostCommentDaoImpl;


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
		PuluoPostCommentDaoImpl comment_dao = new PuluoPostCommentDaoImpl();
		String status = comment_dao.removeCommentUserTimeline(comment_uuid);
		DeleteTimelineCommentResult comment_result = new DeleteTimelineCommentResult(status);
		rawResult = comment_result;
	}
}