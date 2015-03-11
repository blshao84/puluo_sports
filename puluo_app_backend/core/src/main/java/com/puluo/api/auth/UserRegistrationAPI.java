package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserRegistrationResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserRegistrationAPI extends PuluoAPI<PuluoDSI,UserRegistrationResult> {

	private final String mobile;
	private final String password;
	private final String auth_code;
	
	public UserRegistrationAPI(String mobile, String password,String auth_code){
		this(mobile, password,auth_code, DaoApi.getInstance());
	}
	public UserRegistrationAPI(String mobile, String password, String auth_code,PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.password = password;
		this.auth_code = auth_code;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
