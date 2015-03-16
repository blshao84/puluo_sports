package com.puluo.api.result;

import com.puluo.util.HasJSON;

//FIXME: should we send back old and new passwords???
public class UserPasswordUpdateResult extends HasJSON {
	public String password;
	public String new_password;

	public UserPasswordUpdateResult(String password, String new_password) {
		super();
		this.password = password;
		this.new_password = new_password;
	}

	public static UserPasswordUpdateResult dummy() {
		return new UserPasswordUpdateResult(
				"cd8460a5e0f2c2af596f170009bffc02df06b54d",
				"cd8460a5e0f2c2af596f170009bffc02df06b54d");
	}
}
