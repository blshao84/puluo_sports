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
import com.google.gson.Gson;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class PuluoImageService {
	private static final Log LOGGER = LogFactory.getLog(PuluoImageService.class);
	
	private static class ImageUploadResult {
		@SuppressWarnings("unused")
		private String filePath;
		@SuppressWarnings("unused")
		private boolean success;

		public ImageUploadResult(String filePath, boolean success) {
			this.filePath = filePath;
			this.success = success;
		}

		public String toJson() {
			Gson gson = new Gson();
			return gson.toJson(this);
		}
	}

	private static final String spaceName = "puluodev";
	private static final String username = "puluodev";
	private static final String password = "puLuo20!5";
	private static UpYun upyun = new UpYun(spaceName, username, password);

	static {
		upyun.setTimeout(60);
		upyun.setApiDomain(UpYun.ED_AUTO);
	}

	public static void init() {}
	
	public static String saveImage(byte[] data, String filePath) {
		// upyun.setContentMD5(UpYun.md5(data));
		LOGGER.info("sending raw data to upyun ...");
		long t1 = System.currentTimeMillis();
		boolean result = upyun.writeFile(filePath, data, true);
		long t2 = System.currentTimeMillis();
		LOGGER.info(String.format("saving data is done in %s seconds",(t2-t1)/1000.0));
		return new ImageUploadResult(filePath, result).toJson();
	}

	public static void main(String[] args) {
		String fileName = "/Users/blshao/Desktop/alipay3.jpg";
		try {
			File file = new File(fileName);
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://localhost:8080/upload/image");
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