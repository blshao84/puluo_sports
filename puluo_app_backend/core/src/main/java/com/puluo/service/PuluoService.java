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

	private static final String baseSMSURL = "http://v.juhe.cn/sms/send?key=172b8ec174b8a780cd7ac841386ac502&dtype=json&";

	public static final PuluoImageService image = new PuluoImageService(
			new UpYun(upYunSpaceName, upYunUsername, upYunPassword));

	public static final PuluoEmailService email = new PuluoEmailService(
			emailHost, emailUsername, emailPassword);

	public static final JuheSMSClient sms = new JuheSMSClient(
			new DefaultHttpClient(), baseSMSURL);
}
