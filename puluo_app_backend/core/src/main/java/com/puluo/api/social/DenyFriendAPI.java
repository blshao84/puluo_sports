package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DenyFriendResult;

public class DenyFriendAPI extends PuluoAPI<DenyFriendResult> {
	public String user_uuid;
	
	public DenyFriendAPI(String user_uuid) {
		super();
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
