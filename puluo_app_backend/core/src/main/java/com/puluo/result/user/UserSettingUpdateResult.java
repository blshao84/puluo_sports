package com.puluo.result.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class UserSettingUpdateResult extends HasJSON {
	public String user_uuid;
	public List<UserSettingValue> settings;
	
	public UserSettingUpdateResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.settings = new ArrayList<UserSettingValue>();
		this.settings.add(new UserSettingValue("auto_add_friend","允许自动添加好友",auto_add_friend));
		this.settings.add(new UserSettingValue("allow_stranger_view_timeline","允许陌生人浏览",allow_stranger_view_timeline));
		this.settings.add(new UserSettingValue("allow_searched","允许被搜索到",allow_searched));
	}
	
	public static UserSettingUpdateResult dummy() {
		return new UserSettingUpdateResult("", true, true, true);
	}
}
