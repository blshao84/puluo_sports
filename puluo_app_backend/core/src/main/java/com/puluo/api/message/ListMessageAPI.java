package com.puluo.api.message;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListMessageResult;

public class ListMessageAPI extends PuluoAPI<ListMessageResult> {
	public String user_uuid;
	public String since;
	
	public ListMessageAPI(String user_uuid, String since) {
		super();
		this.user_uuid = user_uuid;
		this.since = since;
	}

	@Override
	public ListMessageResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
