package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingUpdateResult;

public class UserSettingUpdateAPI extends PuluoAPI<UserSettingUpdateResult> {
	public boolean auto_add_friend;
	public boolean allow_stranger_view_timeline;
	public boolean allow_searchedl;

	public UserSettingUpdateAPI(boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searchedl) {
		super();
		this.auto_add_friend = auto_add_friend;
		this.allow_stranger_view_timeline = allow_stranger_view_timeline;
		this.allow_searchedl = allow_searchedl;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
