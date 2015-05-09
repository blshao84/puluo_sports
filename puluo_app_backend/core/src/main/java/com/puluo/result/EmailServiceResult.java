package com.puluo.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class EmailServiceResult extends HasJSON{
	public String email;
	public String status;
	
	public EmailServiceResult(String email, String status) {
		super();
		this.email = email;
		this.status = status;
	}
	
	public static EmailServiceResult dummy() {
		return new EmailServiceResult("bshao@163.com", "success");
	}
}
