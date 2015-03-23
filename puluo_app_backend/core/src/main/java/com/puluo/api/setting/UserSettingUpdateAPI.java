package com.puluo.api.setting;

import java.util.Map;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserSettingUpdateAPI extends
		PuluoAPI<PuluoDSI, UserSettingUpdateResult> {
	public Boolean auto_add_friend;
	public Boolean allow_stranger_view_timeline;
	public Boolean allow_searched;

	public UserSettingUpdateAPI(Map<String, String> params) {
		this(
				getBoolOrNull(params, "auto_add_friend"), 
				getBoolOrNull(params,"allow_stranger_view_timeline"), 
				getBoolOrNull(params,"allow_searched"), DaoApi.getInstance());
	}

	public UserSettingUpdateAPI(Boolean auto_add_friend,
			Boolean allow_stranger_view_timeline, Boolean allow_searched) {
		this(auto_add_friend, allow_stranger_view_timeline, allow_searched,
				DaoApi.getInstance());
	}

	public UserSettingUpdateAPI(boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searchedl,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.auto_add_friend = auto_add_friend;
		this.allow_stranger_view_timeline = allow_stranger_view_timeline;
		this.allow_searched = allow_searchedl;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	private static Boolean getBoolOrNull(Map<String, String> params, String key) {
		if (params.containsKey(key)) {
			String value = params.get(key).toLowerCase();
			if (value.equals("true"))
				return true;
			else if (value.equals("false"))
				return false;
			else
				return null;
		} else
			return null;
	}
}
