package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSettingResult;

public class UserSettingAPI extends PuluoAPI<UserSettingResult> {
	public String mobile;
	public String uuid;

	public UserSettingAPI(String mobile, String uuid) {
		super();
		this.mobile = mobile;
		this.uuid = uuid;
	}

	public UserSettingAPI(String mobileOrUUID) {
		super();
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
