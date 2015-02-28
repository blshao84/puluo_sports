package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserSettingUpdateAPI extends PuluoAPI<PuluoDSI,UserSettingUpdateResult> {
	public boolean auto_add_friend;
	public boolean allow_stranger_view_timeline;
	public boolean allow_searched;

	public UserSettingUpdateAPI(boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched){
		this(auto_add_friend, allow_stranger_view_timeline, allow_searched, new DaoApi());
	}
	
	public UserSettingUpdateAPI(boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searchedl, PuluoDSI dsi) {
		this.dsi = dsi;
		this.auto_add_friend = auto_add_friend;
		this.allow_stranger_view_timeline = allow_stranger_view_timeline;
		this.allow_searched = allow_searchedl;
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
