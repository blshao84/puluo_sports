package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserSettingResult extends HasJSON {
	public String user_uuid;
	public boolean auto_add_friend;
	public boolean allow_stranger_view_timeline;
	public boolean allow_searched;
	
	public UserSettingResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.auto_add_friend = auto_add_friend;
		this.allow_stranger_view_timeline = allow_stranger_view_timeline;
		this.allow_searched = allow_searched;
	}
	
	public static UserSettingResult dummy() {
		return new UserSettingResult("", true, true, true);
	}
}
