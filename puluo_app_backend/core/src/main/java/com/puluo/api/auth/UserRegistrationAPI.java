package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserRegistrationResult;

public class UserRegistrationAPI extends PuluoAPI<UserRegistrationResult> {

	public String mobile;
	public String password;
	
	public UserRegistrationAPI(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
