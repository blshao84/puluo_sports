package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileResult;

public class UserProfileAPI extends PuluoAPI<UserProfileResult> {

	public String mobile;
	public String uuid;

	public UserProfileAPI(String mobile, String uuid) {
		super();
		this.mobile = mobile;
		this.uuid = uuid;
	}

	public UserProfileAPI(String mobileOrUUID) {
		super();
		if (isMobile(mobileOrUUID)) {
			this.mobile = mobileOrUUID;
		}
		if (isUUID(mobileOrUUID)) {
			this.uuid = mobileOrUUID;
		}
	}

	@Override
	public UserProfileResult rawResult() {
		// TODO Auto-generated method stub
		return null;
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
