package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.DeleteFriendResult;

public class DeleteFriendAPI extends PuluoAPI<DeleteFriendResult> {
	public String user_uuid;
	
	public DeleteFriendAPI(String user_uuid) {
		super();
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}

}
