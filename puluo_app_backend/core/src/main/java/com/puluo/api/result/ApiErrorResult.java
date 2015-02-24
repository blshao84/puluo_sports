package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class ApiErrorResult extends HasJSON{
	
	public String id;
	public String message;
	public String url;
	
	public ApiErrorResult(String id, String message, String url) {
		super();
		this.id = id;
		this.message = message;
		this.url = url;
	}
	
	

}
