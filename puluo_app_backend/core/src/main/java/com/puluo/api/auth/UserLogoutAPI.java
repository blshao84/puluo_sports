package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLogoutResult;
import com.puluo.entity.PuluoSession;

public class UserLogoutAPI extends PuluoAPI<UserLogoutResult> {


	public PuluoSession session;
	
	
	public UserLogoutAPI(PuluoSession session) {
		super();
		this.session = session;
	}


	@Override
	public UserLogoutResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
