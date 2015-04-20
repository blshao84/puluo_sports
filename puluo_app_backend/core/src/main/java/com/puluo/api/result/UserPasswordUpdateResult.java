package com.puluo.api.result;

import com.puluo.util.HasJSON;

//FIXME: should we send back old and new passwords???
public class UserPasswordUpdateResult extends HasJSON {
	public String auth_code;
	public String new_password;

	public UserPasswordUpdateResult(String auth_code, String new_password) {
		super();
		this.auth_code = auth_code;
		this.new_password = new_password;
	}

	public static UserPasswordUpdateResult dummy() {
		return new UserPasswordUpdateResult(
				"123456",
				"cd8460a5e0f2c2af596f170009bffc02df06b54d");
	}
}
