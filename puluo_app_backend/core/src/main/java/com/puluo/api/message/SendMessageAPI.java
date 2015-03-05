package com.puluo.api.message;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.SendMessageResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class SendMessageAPI extends PuluoAPI<PuluoDSI,SendMessageResult> {
	public String to_uuid;
	public String content;
	public String content_type;
	
	public SendMessageAPI(String to_uuid, String content, String content_type){
		this(to_uuid, content, content_type, DaoApi.getInstance());
	}
	public SendMessageAPI(String to_uuid, String content, String content_type, PuluoDSI dsi) {
		this.dsi = dsi;
		this.to_uuid = to_uuid;
		this.content = content;
		this.content_type = content_type;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}
