package com.puluo.app;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import com.puluo.service.PuluoService;

public class ImageUploader {

	public static void main(String[] args) {
		File file = new File("/Users/blshao/Downloads/empty.jpeg");
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
		} 
		byte[] bytes = bos.toByteArray();
		String status = 
				PuluoService.image.saveImage(bytes, "empty.jpg").status;
		System.out.println(status);
	}

}
