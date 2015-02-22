package com.puluo.api.result;

import com.google.gson.Gson;
import com.puluo.util.HasJSON;


public class ImageUploadServiceResult extends HasJSON{
	public String image_link;
	public String status;
	
	public ImageUploadServiceResult(String image_link, String status) {
		super();
		this.image_link = image_link;
		this.status = status;
	}
	
	public static ImageUploadServiceResult dummy() {
		return new ImageUploadServiceResult("http://upyun.com/puluo/xxxx", "success");
	}
}
