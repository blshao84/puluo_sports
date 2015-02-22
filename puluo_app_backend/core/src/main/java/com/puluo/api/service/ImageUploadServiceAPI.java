package com.puluo.api.service;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ImageUploadServiceResult;


public class ImageUploadServiceAPI extends PuluoAPI<ImageUploadServiceResult> {

	public String email_type;

	public ImageUploadServiceAPI(String email_type) {
		super();
		this.email_type = email_type;
	}

	@Override
	public ImageUploadServiceResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}
}