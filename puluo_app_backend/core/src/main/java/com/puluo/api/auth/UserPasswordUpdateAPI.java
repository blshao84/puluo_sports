package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserPasswordUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserPasswordUpdateAPI extends PuluoAPI<PuluoDSI,UserPasswordUpdateResult> {
	public String userUUID;
	public String password;
	public String new_password;
	
	public UserPasswordUpdateAPI(String userUUID,String password, String new_password){
		this(userUUID, password, new_password, new DaoApi());
	}
	
	public UserPasswordUpdateAPI(String userUUID,String password, String new_password, PuluoDSI dsi) {
		this.dsi = dsi;
		this.userUUID = userUUID;
		this.password = password;
		this.new_password = new_password;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
