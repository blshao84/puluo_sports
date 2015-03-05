package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserRegistrationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserRegistrationAPI extends PuluoAPI<PuluoDSI,UserRegistrationResult> {

	public String mobile;
	public String password;
	
	public UserRegistrationAPI(String mobile, String password){
		this(mobile, password, DaoApi.getInstance());
	}
	public UserRegistrationAPI(String mobile, String password, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.password = password;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
