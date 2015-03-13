package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserRegistrationResult extends HasJSON {
	public String user_uuid;
	public String mobile;
	public String password;

	public UserRegistrationResult(String user_uuid,String mobile, String password) {
		super();
		this.user_uuid = user_uuid;
		this.mobile = mobile;
		this.password = password;
	}

	public static UserRegistrationResult dummy() {
		return new UserRegistrationResult(
				"cd8460a5e0f2c2af596f170009bffc02df06b54d", "12346789000",
				"cd8460a5e0f2c2af596f170009bffc02df06b54d");
	}
}
