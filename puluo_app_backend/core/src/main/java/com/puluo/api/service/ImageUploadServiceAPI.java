package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ImageUploadServiceResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;


public class ImageUploadServiceAPI extends PuluoAPI<PuluoDSI,ImageUploadServiceResult> {

	public String email_type;

	public ImageUploadServiceAPI(String email_type){
		this(email_type, new DaoApi());
	}
	public ImageUploadServiceAPI(String email_type, PuluoDSI dsi) {
		this.dsi = dsi;
		this.email_type = email_type;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
}