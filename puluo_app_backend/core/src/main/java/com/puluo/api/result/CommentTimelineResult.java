package com.puluo.api.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;

public class CommentTimelineResult extends HasJSON{
	
	public String status;

	public CommentTimelineResult(String status) {
		super();
		this.status = status;
	}
	
	public static CommentTimelineResult dummy() {
		return new CommentTimelineResult("success");
	}
}
