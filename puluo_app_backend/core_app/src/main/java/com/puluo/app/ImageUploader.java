package com.puluo.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.puluo.result.ImageUploadServiceResult;
import com.puluo.service.PuluoService;

public class ImageUploader {

	public static void main(String[] args) {
		File file = new File("/Users/blshao/Desktop/puluo/puluo logo.jpg");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		    for (int readNum; (readNum = fis.read(buf)) != -1;) {
		        bos.write(buf, 0, readNum); 
		        System.out.println("read " + readNum + " bytes,");
		    }
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		byte[] bytes = bos.toByteArray();
		ImageUploadServiceResult status = 
				PuluoService.image.saveImage(bytes, "empty_head");
		System.out.println(status.toJson());
	}

}
