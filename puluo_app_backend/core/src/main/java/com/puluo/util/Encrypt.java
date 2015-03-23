package com.puluo.util;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class Encrypt {
	public static String sha256(String text){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(text.getBytes("UTF-8"));
			byte[] digest = md.digest();
			return DatatypeConverter.printHexBinary(digest);
			//return new String(digest,"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args){
		System.out.println(sha256("tracey"));
	}
}
