package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DenyFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class DenyFriendAPI extends PuluoAPI<PuluoDSI,DenyFriendResult> {
	public String user_uuid;
	
	public DenyFriendAPI(String user_uuid){
		this(user_uuid, new DaoApi());
	}
	public DenyFriendAPI(String user_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
