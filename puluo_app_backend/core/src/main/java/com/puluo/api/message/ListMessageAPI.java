package com.puluo.api.message;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListMessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ListMessageAPI extends PuluoAPI<PuluoDSI,ListMessageResult> {
	public String user_uuid;
	public String since;
	
	public ListMessageAPI(String user_uuid, String since){
		this(user_uuid, since, new DaoApi());
	}
	public ListMessageAPI(String user_uuid, String since, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
		this.since = since;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
