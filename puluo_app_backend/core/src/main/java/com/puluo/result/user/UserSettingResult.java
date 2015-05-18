package com.puluo.result.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class UserSettingResult extends HasJSON {
	public String user_uuid;
	public List<UserSettingValue> settings;
	
	public UserSettingResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.settings = new ArrayList<UserSettingValue>();
		this.settings.add(new UserSettingValue("auto_add_friend","允许自动添加好友",auto_add_friend));
		this.settings.add(new UserSettingValue("allow_stranger_view_timeline","允许陌生人浏览",allow_stranger_view_timeline));
		this.settings.add(new UserSettingValue("allow_searched","允许被搜索到",allow_searched));
	}
	
	public String legacyResult() {
		return String.format("{"
				+ "\"user_uuid\":\"%s\","
				+ "\"settings\":{\"auto_add_friend\":%s,"
				+ "\"allow_stranger_view_timeline\":%s,"
				+ "\"allow_searched\":%s}}", 
				user_uuid,
				settings.get(0).value,
				settings.get(1).value,
				settings.get(2).value);
	}
	
	public static UserSettingResult dummy() {
		return new UserSettingResult("", true, true, true);
	}
	

}
