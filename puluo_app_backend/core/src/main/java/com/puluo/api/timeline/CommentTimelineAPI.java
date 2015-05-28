package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.CommentTimelineResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class CommentTimelineAPI extends PuluoAPI<PuluoDSI,CommentTimelineResult> {
	public static Log log = LogFactory.getLog(CommentTimelineAPI.class);
	public String timeline_uuid;
	public String comment;
	public String reply_to;
	public String replier;

	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to, String replier){
		this(timeline_uuid, comment, reply_to, replier, DaoApi.getInstance());
	}
	
	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to, String replier, PuluoDSI dsi) {
		this.dsi = dsi;
		this.timeline_uuid = timeline_uuid;
		this.comment = comment;
		this.reply_to = reply_to;
		this.replier = replier;
	}

	@Override
	public void execute() {
		log.info(String.format("开始添加时间线%s的评论"),timeline_uuid);
		PuluoTimelineCommentDao comment_dao = dsi.postCommentDao();
		String status = comment_dao.saveTimelineComment(timeline_uuid,replier,reply_to,comment);
		if(status.equals("success")) {
			CommentTimelineResult comment_result = new CommentTimelineResult(status);
			rawResult = comment_result;
		} else {
			log.error(String.format("添加时间线评论失败"));
			this.error = ApiErrorResult.getError(26);
		}
	}
}