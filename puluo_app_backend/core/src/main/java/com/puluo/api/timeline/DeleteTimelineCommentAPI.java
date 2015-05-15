package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.DeleteTimelineCommentResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class DeleteTimelineCommentAPI extends PuluoAPI<PuluoDSI,DeleteTimelineCommentResult> {
	public static Log log = LogFactory.getLog(DeleteTimelineCommentAPI.class);
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
		log.info(String.format("开始删除时间线评论%s",comment_uuid));
		PuluoTimelineCommentDao comment_dao = dsi.postCommentDao();
		String status = comment_dao.removeTimelineComment(comment_uuid);
		if(status.equals("success")) {
			DeleteTimelineCommentResult comment_result = new DeleteTimelineCommentResult(status);
			rawResult = comment_result;
		} else {
			log.error(String.format("删除时间线评论失败"));
			this.error = ApiErrorResult.getError(27);
		}
	}
}