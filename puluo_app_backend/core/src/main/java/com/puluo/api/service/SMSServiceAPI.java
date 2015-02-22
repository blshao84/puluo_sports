package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.SMSServiceResult;


public class SMSServiceAPI extends PuluoAPI<SMSServiceResult> {

	public String sms_type;

	public SMSServiceAPI(String sms_type) {
		super();
		this.sms_type = sms_type;
	}

	@Override
	public SMSServiceResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}