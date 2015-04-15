package com.puluo.service;

import java.io.File;
import java.io.IOException;

import main.java.com.UpYun;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.puluo.api.result.ImageUploadServiceResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoImageService {
	private final Log LOGGER = LogFactory.getLog(PuluoImageService.class);



	private final UpYun upyun;
	
	public PuluoImageService(UpYun upyun) {
		this.upyun = upyun;
	}
	

	public ImageUploadServiceResult saveImage(byte[] data, String filePath) {
		// upyun.setContentMD5(UpYun.md5(data));
		LOGGER.info("sending raw data to upyun ...");
		long t1 = System.currentTimeMillis();
		boolean result = upyun.writeFile(filePath, data, true);
		long t2 = System.currentTimeMillis();
		LOGGER.info(String.format("saving data is done in %s seconds",
				(t2 - t1) / 1000.0));
		String status;
		if(result) status = "success"; else status = "failed";
		return new ImageUploadServiceResult(filePath, status);
	}

	public static void main(String[] args) {
		String fileName = "/Users/blshao/Downloads/kcb.jpg";
		try {
			File file = new File(fileName);
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:8080/services/image");
			MultipartEntity entity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file);
			entity.addPart("uploadedfile", cbFile);
			post.setEntity(entity);
			HttpResponse response2 = httpClient.execute(post);
			System.out.println(response2.getStatusLine().getStatusCode());
			post.releaseConnection();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
