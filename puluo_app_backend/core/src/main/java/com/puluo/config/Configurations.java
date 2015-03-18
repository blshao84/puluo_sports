package com.puluo.config;

import com.puluo.service.WechatKey;

public class Configurations {
	public static long orderIDBase = 10000;
	public static String webServer = "";
	public static boolean enableSMSNotification = true;

	public static String wechatToken = "puluosports";
	public static String wechatAppId = "wx5d9079331e9af5e3";
	public static String wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
	public static WechatKey wechatKey = new WechatKey(wechatAppKey,wechatAppId);
}
