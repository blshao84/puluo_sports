package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLoginResult;

public class UserLoginAPI extends PuluoAPI<UserLoginResult> {

	public String mobile;
	public String password;
	
	public UserLoginAPI(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}

	@Override
	public UserLoginResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
