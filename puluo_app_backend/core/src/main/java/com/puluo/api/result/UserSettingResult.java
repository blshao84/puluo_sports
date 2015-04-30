package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserSettingResult extends HasJSON {
	public String user_uuid;
	public SettingPair auto_add_friend;
	public SettingPair allow_stranger_view_timeline;
	public SettingPair allow_searched;
	
	public UserSettingResult(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched) {
		super();
		this.user_uuid = user_uuid;
		this.auto_add_friend = new SettingPair("允许自动添加好友",auto_add_friend);
		this.allow_stranger_view_timeline =  new SettingPair("允许陌生人浏览",allow_stranger_view_timeline);
		this.allow_searched =  new SettingPair("允许被搜索到",allow_searched);
	}
	
	public String legacyResult() {
		return String.format("{"
				+ "\"user_uuid\":\"%s\","
				+ "\"auto_add_friend\":%s,"
				+ "\"allow_stranger_view_timeline\":%s,"
				+ "\"allow_searched\":%s}", 
				user_uuid,
				auto_add_friend.value,
				allow_stranger_view_timeline.value,
				allow_searched.value);
	}
	
	public static UserSettingResult dummy() {
		return new UserSettingResult("", true, true, true);
	}
	
	private class SettingPair {
		@SuppressWarnings("unused")
		public String name;
		@SuppressWarnings("unused")
		public boolean value;
		public SettingPair(String name, boolean value) {
			super();
			this.name = name;
			this.value = value;
		}
		
	}
}
