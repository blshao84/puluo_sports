package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserLoginResult extends HasJSON{
	public String mobile;
	public String password;
	public UserLoginResult(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}
	
	public static UserLoginResult dummy() {
		return new UserLoginResult("12346789000","cd8460a5e0f2c2af596f170009bffc02df06b54d");
	}
}
