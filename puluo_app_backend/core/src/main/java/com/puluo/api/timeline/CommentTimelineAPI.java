package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.CommentTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPostCommentDaoImpl;


public class CommentTimelineAPI extends PuluoAPI<PuluoDSI,CommentTimelineResult> {

	public String timeline_uuid;
	public String comment;
	public String reply_to;

	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to){
		this(timeline_uuid, comment, reply_to, DaoApi.getInstance());
	}
	
	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to, PuluoDSI dsi) {
		this.dsi = dsi;
		this.timeline_uuid = timeline_uuid;
		this.comment = comment;
		this.reply_to = reply_to;
	}

	@Override
	public void execute() {
		PuluoPostCommentDaoImpl comment_dao = new PuluoPostCommentDaoImpl();
		String status = comment_dao.commentUserTimeline(timeline_uuid, reply_to, comment);
		CommentTimelineResult comment_result = new CommentTimelineResult(status);
		rawResult = comment_result;
	}
}