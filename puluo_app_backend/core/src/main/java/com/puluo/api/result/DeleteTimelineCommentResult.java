package com.puluo.api.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class DeleteTimelineCommentResult extends HasJSON{
	public String status;

	public DeleteTimelineCommentResult(String status) {
		super();
		this.status = status;
	}
	
	public static DeleteTimelineCommentResult dummy() {
		return new DeleteTimelineCommentResult("success");
	}
}