package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLogoutResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;

public class UserLogoutAPI extends PuluoAPI<PuluoDSI,UserLogoutResult> {


	public PuluoSession session;
	
	public UserLogoutAPI(PuluoSession session){
		this(session, DaoApi.getInstance());
	}
	public UserLogoutAPI(PuluoSession session, PuluoDSI dsi) {
		this.dsi = dsi;
		this.session = session;
	}


	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
