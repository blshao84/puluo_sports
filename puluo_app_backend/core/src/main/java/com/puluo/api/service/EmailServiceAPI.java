package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.result.EmailServiceResult;


public class EmailServiceAPI extends PuluoAPI<PuluoDSI,EmailServiceResult> {

	public String email_type;

	public EmailServiceAPI(String email_type){
		this(email_type, DaoApi.getInstance());
	}
	public EmailServiceAPI(String email_type, PuluoDSI dsi) {
		this.dsi = dsi;
		this.email_type = email_type;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}