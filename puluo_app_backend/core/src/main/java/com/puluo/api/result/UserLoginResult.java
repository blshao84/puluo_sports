package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserLoginResult extends HasJSON {
	public String uuid;
	public long created_at;
	public long last_login;

	public UserLoginResult(String uuid, long created_at,long last_login) {
		super();
		this.uuid = uuid;
		this.created_at = created_at;
		this.last_login = last_login;
	}

	public static UserLoginResult dummy() {
		return new UserLoginResult("cd8460a5e0f2c2af596f170009bffc02df06b54d",
				1427007059034L,1427007059034L);
	}
}
