package com.puluo.api.result;

import com.puluo.util.HasJSON;


public class UserLogoutResult extends HasJSON {
	public String uuid;
	public long duration_seconds;
	public UserLogoutResult(String uuid, long duration_seconds) {
		super();
		this.uuid = uuid;
		this.duration_seconds = duration_seconds;
	}
	
	public static UserLogoutResult dummy(){
		return new UserLogoutResult("de305d54-75b4-431b-adb2-eb6b9e546013",12345);
	}
}
