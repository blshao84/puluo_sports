package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApproveFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ApproveFriendAPI extends PuluoAPI<PuluoDSI,ApproveFriendResult> {
	public String user_uuid;
	
	public ApproveFriendAPI(String user_uuid){
		this(user_uuid, new DaoApi());
	}
	public ApproveFriendAPI(String user_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
