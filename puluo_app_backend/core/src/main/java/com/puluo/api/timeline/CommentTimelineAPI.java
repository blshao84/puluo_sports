package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.CommentTimelineResult;


public class CommentTimelineAPI extends PuluoAPI<CommentTimelineResult> {

	public String timeline_uuid;
	public String comment;
	public String reply_to;

	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to) {
		super();
		this.timeline_uuid = timeline_uuid;
		this.comment = comment;
		this.reply_to = reply_to;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}