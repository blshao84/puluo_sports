package com.puluo.api.timeline;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.CommentTimelineResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class CommentTimelineAPI extends PuluoAPI<PuluoDSI,CommentTimelineResult> {

	public String timeline_uuid;
	public String comment;
	public String reply_to;

	public CommentTimelineAPI(String timeline_uuid, String comment,
			String reply_to){
		this(timeline_uuid, comment, reply_to, new DaoApi());
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
		// TODO Auto-generated method stub
		
	}
}