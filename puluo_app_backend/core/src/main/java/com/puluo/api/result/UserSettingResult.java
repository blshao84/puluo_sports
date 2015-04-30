package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserSettingResult extends HasJSON {
	public String user_uuid;
	public SettingPai auto_add_friend;
	public SettingPai allow_stranger_view_timeline;
	public SettingPai allow_searched;
	
	public UserSettingResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.auto_add_friend = new SettingPai("允许自动添加好友",auto_add_friend);
		this.allow_stranger_view_timeline =  new SettingPai("允许陌生人浏览",allow_stranger_view_timeline);
		this.allow_searched =  new SettingPai("允许被搜索到",allow_searched);
	}
	
	public static UserSettingResult dummy() {
		return new UserSettingResult("", true, true, true);
	}
	
	private class SettingPai {
		@SuppressWarnings("unused")
		public String name;
		@SuppressWarnings("unused")
		public boolean value;
		public SettingPai(String name, boolean value) {
			super();
			this.name = name;
			this.value = value;
		}
		
	}
}
