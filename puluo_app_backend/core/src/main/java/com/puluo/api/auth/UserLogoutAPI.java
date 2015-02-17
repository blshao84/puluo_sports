package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLogoutResult;

public class UserLogoutAPI extends PuluoAPI<UserLogoutResult> {

	/**
	 * user uuid
	 */
	public String uuid;
	
	
	public UserLogoutAPI(String uuid) {
		super();
		this.uuid = uuid;
	}


	@Override
	public UserLogoutResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
