package com.puluo.api.message;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ListMessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ListMessageAPI extends PuluoAPI<PuluoDSI,ListMessageResult> {
	public String from_user_uuid;
	public String to_user_uuid;
	public DateTime since;
	
	public ListMessageAPI(String from_user_uuid,String to_user_uuid, DateTime since){
		this(from_user_uuid,to_user_uuid, since, DaoApi.getInstance());
	}
	public ListMessageAPI(String from_user_uuid,String to_user_uuid, DateTime since, PuluoDSI dsi) {
		this.dsi = dsi;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.since = since;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
