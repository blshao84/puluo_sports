package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserPasswordUpdateResult;

public class UserPasswordUpdateAPI extends PuluoAPI<UserPasswordUpdateResult> {
	public String userUUID;
	public String password;
	public String new_password;
	
	
	public UserPasswordUpdateAPI(String userUUID,String password, String new_password) {
		super();
		this.userUUID = userUUID;
		this.password = password;
		this.new_password = new_password;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
