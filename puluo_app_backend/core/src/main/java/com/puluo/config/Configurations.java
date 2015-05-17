package com.puluo.config;

import com.puluo.util.Strs;
import com.puluo.weichat.WechatKey;

public class Configurations {
	public static final long orderIDBase = 10000;
	public static final String webServer = "www.puluosports.com";
	public static final boolean enableSMSNotification = true;
	public static final String imageServer ="http://img.puluosports.com/";

	public static final String wechatToken = "puluosports";
	public static final String wechatAppId = "wx5d9079331e9af5e3";
	public static final String wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
	public static final WechatKey wechatKey = new WechatKey(wechatAppKey,wechatAppId);
	
	public static final String[] wechatButtonInfo1List = {
		"IMQeaQEwrJVtUoS1W_GkVYci8RrdN0kkTuqzH8oXwcI",
		"ZTOGRA6tGSontcNb6tVGLNbJ029gQlmqrHMiCRwO6Ww"};
	public static final String[] wechatButtonInfo2List = {
		"jjDWGK-OKR98IkzcySpOMWIsPUo07i4oRoDLOfL6pGE",
		"jjDWGK-OKR98IkzcySpOMUJCAN5B3TSnABKDs6Ntrfk"};
	public static final String[] wechatButtonInfo3List = {
		"jjDWGK-OKR98IkzcySpOMbU9U8CATWKFUURXXob0spI",
		"jjDWGK-OKR98IkzcySpOMRzqhRRCPQ4bz6Ji7T25_cc"};
	public static final String[] wechatButtonInfo4List = {
		"jjDWGK-OKR98IkzcySpOMd3flhG2JL7WaHCzKt4IIW4"};
	public static final String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
	public static final String emptyImage = Strs.join(imageServer,"empty.jpg");
	
	public static final Double minPrice = 0.1;
}
