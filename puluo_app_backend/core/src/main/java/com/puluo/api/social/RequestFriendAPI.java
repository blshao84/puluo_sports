package com.puluo.api.social;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.RequestFriendResult;

public class RequestFriendAPI extends PuluoAPI<RequestFriendResult> {
	public String user_uuid;
	
	public RequestFriendAPI(String user_uuid) {
		super();
		this.user_uuid = user_uuid;
	}

	@Override
	public RequestFriendResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
