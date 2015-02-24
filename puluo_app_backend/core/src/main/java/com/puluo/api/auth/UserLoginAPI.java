package com.puluo.api.auth;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLoginResult;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.impl.PuluoSessionImpl;

public class UserLoginAPI extends PuluoAPI<UserLoginResult> {

	public String mobile;
	public String password;
	
	
	public UserLoginAPI(String mobile, String password) {
		super();
		this.mobile = mobile;
		this.password = password;
	}
	
	public PuluoSession obtainSession(){
		return new PuluoSessionImpl("","",DateTime.now());
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
