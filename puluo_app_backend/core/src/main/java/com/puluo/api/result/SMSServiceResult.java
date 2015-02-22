package com.puluo.api.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class SMSServiceResult extends HasJSON{
	public String email;
	public String status;
	
	public SMSServiceResult(String email, String status) {
		super();
		this.email = email;
		this.status = status;
	}
	
	public static SMSServiceResult dummy() {
		return new SMSServiceResult("bshao@163.com", "success");
	}
}
