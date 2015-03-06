package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserLoginResult extends HasJSON {
	public String uuid;
	public String created_at;

	public UserLoginResult(String uuid, String created_at) {
		super();
		this.uuid = uuid;
		this.created_at = created_at;
	}

	public static UserLoginResult dummy() {
		return new UserLoginResult("cd8460a5e0f2c2af596f170009bffc02df06b54d",
				"2012-01-01 12:00:00");
	}
}
