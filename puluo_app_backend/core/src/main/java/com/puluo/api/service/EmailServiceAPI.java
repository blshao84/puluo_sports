package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.EmailServiceResult;


public class EmailServiceAPI extends PuluoAPI<EmailServiceResult> {

	public String email_type;

	public EmailServiceAPI(String email_type) {
		super();
		this.email_type = email_type;
	}

	@Override
	public EmailServiceResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}