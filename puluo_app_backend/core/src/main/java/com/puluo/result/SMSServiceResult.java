package com.puluo.result;

import com.puluo.util.HasJSON;


public class SMSServiceResult extends HasJSON{
	public String mobile;
	public String status;
	
	public SMSServiceResult(String mobile, String status) {
		super();
		this.mobile = mobile;
		this.status = status;
	}
	
	public static SMSServiceResult dummy() {
		return new SMSServiceResult("1234567890", "success");
	}
}
