package com.puluo.api.message;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.SendMessageResult;

public class SendMessageAPI extends PuluoAPI<SendMessageResult> {
	public String to_uuid;
	public String content;
	public String content_type;
	
	public SendMessageAPI(String to_uuid, String content, String content_type) {
		super();
		this.to_uuid = to_uuid;
		this.content = content;
		this.content_type = content_type;
	}

	@Override
	public SendMessageResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}
