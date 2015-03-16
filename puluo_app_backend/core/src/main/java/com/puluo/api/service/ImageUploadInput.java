package com.puluo.api.service;

public class ImageUploadInput {
	public String fileName;
	public byte[] data;
	
	public ImageUploadInput(String fileName, byte[] data) {
		super();
		this.fileName = fileName;
		this.data = data;
	}
	
	
}
