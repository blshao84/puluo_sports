package com.puluo.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class LikeTimelineResult extends HasJSON{
	public String status;

	public LikeTimelineResult(String status) {
		super();
		this.status = status;
	}
	
	public static LikeTimelineResult dummy() {
		return new LikeTimelineResult("success");
	}
}