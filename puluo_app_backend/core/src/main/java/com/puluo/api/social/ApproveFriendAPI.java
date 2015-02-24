package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApproveFriendResult;

public class ApproveFriendAPI extends PuluoAPI<ApproveFriendResult> {
	public String user_uuid;
	
	public ApproveFriendAPI(String user_uuid) {
		super();
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
