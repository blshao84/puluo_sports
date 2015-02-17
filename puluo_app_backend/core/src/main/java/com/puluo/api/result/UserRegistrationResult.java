package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserRegistrationResult extends HasJSON{
	public String mobile;
	public String password;
	public UserRegistrationResult(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}
	
	public static UserRegistrationResult dummy() {
		return new UserRegistrationResult("12346789000","cd8460a5e0f2c2af596f170009bffc02df06b54d");
	}
}
