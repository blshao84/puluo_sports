package com.puluo.result.user;

import com.puluo.util.HasJSON;

public class UserSettingUpdateResult extends HasJSON {
	public String user_uuid;
	public UserSettingValue auto_add_friend;
	public UserSettingValue allow_stranger_view_timeline;
	public UserSettingValue allow_searched;
	
	public UserSettingUpdateResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.auto_add_friend = new UserSettingValue("auto_add_friend","允许自动添加好友",auto_add_friend);
		this.allow_stranger_view_timeline =  new UserSettingValue("allow_stranger_view_timeline","允许陌生人浏览",allow_stranger_view_timeline);
		this.allow_searched =  new UserSettingValue("allow_searched","允许被搜索到",allow_searched);
	}
	
	public static UserSettingUpdateResult dummy() {
		return new UserSettingUpdateResult("", true, true, true);
	}
}
