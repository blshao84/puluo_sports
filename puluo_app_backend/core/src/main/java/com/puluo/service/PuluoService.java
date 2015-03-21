package com.puluo.service;

import main.java.com.UpYun;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * PuluoService is the centralized place for all external services api
 */
public class PuluoService {

	private static final String upYunSpaceName = "puluodev";
	private static final String upYunUsername = "puluodev";
	private static final String upYunPassword = "puLuo20!5";

	private static final String emailHost = "smtpcloud.sohu.com";
	private static final String emailUsername = "postmaster@unuotech.sendcloud.org";
	private static final String emailPassword = "zkW71LVkBOsxkACe";

	private static final String baseSMSURL = "http://v.juhe.cn/sms/send?key=623b3ce6bcd4a51ee7930b025451b441&dtype=json&";

	public static final PuluoImageService image = new PuluoImageService(
			new UpYun(upYunSpaceName, upYunUsername, upYunPassword));

	public static final PuluoEmailService email = new PuluoEmailService(
			emailHost, emailUsername, emailPassword);

	public static final JuheSMSClient sms = new JuheSMSClient(
			new DefaultHttpClient(), baseSMSURL);

	public static JuheSMSClient getSms() {
		return sms;
	}

	public static PuluoEmailService getEmail() {
		return email;
	}

	public static PuluoImageService getImage() {
		return image;
	}
	
	public static void main(String[] args){
		
		System.out.println(sms.sendAuthCode("18646655333", "123456"));
	}
}
