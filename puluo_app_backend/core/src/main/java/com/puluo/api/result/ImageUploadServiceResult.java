package com.puluo.api.result;

import com.puluo.util.HasJSON;
import com.puluo.util.Strs;


public class ImageUploadServiceResult extends HasJSON{
	public final String image_link;
	public final int sizeInKB;
	public final String status;
	
	public ImageUploadServiceResult(String image_link, String status, int kb) {
		super();
		this.image_link = image_link;
		this.status = status;
		this.sizeInKB = kb;
	}
	
	
	public String thumbnai() {
		return Strs.join(image_link,"!small");
	}
	
	public boolean isSuccess() {
		return status.equals("success");
	}
	
	@Override
	public String toString() {
		return "ImageUploadServiceResult [image_link=" + image_link
				+ ", status=" + status + "]";
	}



	public static ImageUploadServiceResult dummy() {
		return new ImageUploadServiceResult("http://upyun.com/puluo/xxxx", "success",100);
	}
}
