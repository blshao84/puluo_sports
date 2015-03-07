package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class AlipaymentResult extends HasJSON {
	private final boolean is_success;
	private final String error_message;
	
	public AlipaymentResult(boolean is_success, String error_message) {
		super();
		this.is_success = is_success;
		this.error_message = error_message;
	}

	public boolean isSuccess() {
		
		return is_success;
	}
	
	public String error(){
		return error_message;
	}
}
