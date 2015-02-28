package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserProfileAPI extends PuluoAPI<PuluoDSI,UserProfileResult> {

	public String mobile;
	public String uuid;

	public UserProfileAPI(String mobile, String uuid){
		this(mobile, uuid, new DaoApi());
	}
	public UserProfileAPI(String mobile, String uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.uuid = uuid;
	}

	public UserProfileAPI(String mobileOrUUID){
		this(mobileOrUUID, new DaoApi());
	}
	public UserProfileAPI(String mobileOrUUID, PuluoDSI dsi) {
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
