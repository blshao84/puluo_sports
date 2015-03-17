package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.CommentTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class CommentTimelineAPI extends PuluoAPI<PuluoDSI,CommentTimelineResult> {
	public static Log log = LogFactory.getLog(CommentTimelineAPI.class);
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
		log.info(String.format("开始添加时间线%s的评论"),timeline_uuid);
		PuluoPostCommentDao comment_dao = dsi.postCommentDao();
		String status = comment_dao.commentUserTimeline(timeline_uuid,reply_to,comment);
		if(status.equals("success")) {
			CommentTimelineResult comment_result = new CommentTimelineResult(status);
			rawResult = comment_result;
		} else {
			log.error(String.format("添加时间线评论失败"));
			this.error = ApiErrorResult.getError(26);
		}
	}
}