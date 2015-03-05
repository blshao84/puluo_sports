package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.SMSServiceResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class SMSServiceAPI extends PuluoAPI<PuluoDSI,SMSServiceResult> {

	public String sms_type;

	public SMSServiceAPI(String sms_type){
		this(sms_type, DaoApi.getInstance());
	}
	public SMSServiceAPI(String sms_type, PuluoDSI dsi) {
		this.dsi = dsi;
		this.sms_type = sms_type;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}