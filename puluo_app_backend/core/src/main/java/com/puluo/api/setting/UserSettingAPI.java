package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserSettingAPI extends PuluoAPI<PuluoDSI,UserSettingResult> {
	public String mobile;
	public String uuid;

	public UserSettingAPI(String mobile, String uuid) {
		this(mobile, uuid, DaoApi.getInstance());
	}

	public UserSettingAPI(String mobileOrUUID) {
		this(mobileOrUUID, DaoApi.getInstance());
	}
	
	
	public UserSettingAPI(String mobile, String uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.uuid = uuid;
	}

	public UserSettingAPI(String mobileOrUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		if (isMobile(mobileOrUUID)) {
			this.mobile = mobileOrUUID;
		}
		if (isUUID(mobileOrUUID)) {
			this.uuid = mobileOrUUID;
		}
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

	private boolean isMobile(String m) {
		// TODO
		return true;
	}

	private boolean isUUID(String u) {
		// TODO
		return true;
	}

}
