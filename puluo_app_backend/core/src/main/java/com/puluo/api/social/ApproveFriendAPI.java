package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApproveFriendResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ApproveFriendAPI extends PuluoAPI<PuluoDSI,ApproveFriendResult> {
	private final String to_user_uuid;
	private final String from_user_uuid;
	
	public ApproveFriendAPI(String to_user_uuid, String from_user_uuid){
		this(to_user_uuid,from_user_uuid, DaoApi.getInstance());
	}
	public ApproveFriendAPI(String to_user_uuid, String from_user_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.to_user_uuid = to_user_uuid;
		this.from_user_uuid = from_user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
