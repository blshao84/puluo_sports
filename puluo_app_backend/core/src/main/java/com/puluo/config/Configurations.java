package com.puluo.config;

import com.puluo.enumeration.PuluoEnvironment;
import com.puluo.util.Strs;
import com.puluo.weichat.WechatKey;

public class Configurations {
	public static final PuluoEnvironment env = PuluoEnvironment
			.getEnvironment();
	public static final long orderIDBase = 10000;
	public static final String webServer = "www.puluosports.com";
	public static final boolean enableSMSNotification = true;
	public static final String imageServer = "http://img.puluosports.com/";

	private static String wechatToken;
	private static String wechatAppId;
	private static String wechatAppKey;

	public static final String[] wechatButtonInfo1List = {
			"IMQeaQEwrJVtUoS1W_GkVYci8RrdN0kkTuqzH8oXwcI",
			"ZTOGRA6tGSontcNb6tVGLNbJ029gQlmqrHMiCRwO6Ww" };
	public static final String[] wechatButtonInfo2List = {
			"jjDWGK-OKR98IkzcySpOMWIsPUo07i4oRoDLOfL6pGE",
			"jjDWGK-OKR98IkzcySpOMUJCAN5B3TSnABKDs6Ntrfk" };
	public static final String[] wechatButtonInfo3List = {
			"jjDWGK-OKR98IkzcySpOMbU9U8CATWKFUURXXob0spI",
			"jjDWGK-OKR98IkzcySpOMRzqhRRCPQ4bz6Ji7T25_cc" };
	public static final String[] wechatButtonInfo4List = { "jjDWGK-OKR98IkzcySpOMd3flhG2JL7WaHCzKt4IIW4" };
	public static final String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
	public static final String emptyImage = Strs.join(imageServer, "empty.jpg");

	public static final Double minPrice = 0.1;

	static {
		switch (env) {
		case PROD:
			wechatToken = "puluosports";
			wechatAppId = "wx5d9079331e9af5e3";
			wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
			break;
		default:
			wechatToken = "wwwdqxmtcom";
			wechatAppId = "wx002b167e928b4951";
			wechatAppKey = "faa1e7c66e8be5f635e10f069b54af35";
			break;
		}
	}
	
	public static  WechatKey wechatKey() {
		return new WechatKey(wechatAppKey,wechatAppId);
	}
	
	public static String wechatToken() {
		return wechatToken;
	}
	
	public static String wechatAppId() {
		return wechatAppId;
	}
	
	public static String wechatAppKey() {
		return wechatAppKey;
	}
}
