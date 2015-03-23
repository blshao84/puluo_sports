package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserSettingAPI extends PuluoAPI<PuluoDSI, UserSettingResult> {
	public String user_uuid;

	public UserSettingAPI(String user_uuid) {
		this(user_uuid, DaoApi.getInstance());
	}

	public UserSettingAPI(String mobileOrUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
}
