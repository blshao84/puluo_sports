package com.puluo.config;

import com.puluo.weichat.WechatKey;

public class Configurations {
	public static long orderIDBase = 10000;
	public static String webServer = "";
	public static boolean enableSMSNotification = true;
	public static String imageServer ="http://puluodev.b0.upaiyun.com/";

	public static String wechatToken = "puluosports";
	public static String wechatAppId = "wx5d9079331e9af5e3";
	public static String wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
	public static WechatKey wechatKey = new WechatKey(wechatAppKey,wechatAppId);
	
	public static String[] wechatButtonInfo1List = {"CpJlAUkrFja6edvm_4Ma-ifqXo6g15Esccu90lAsnAw"};
	public static String[] wechatButtonInfo2List = {"CpJlAUkrFja6edvm_4Ma-knnlUZFbbf-W6YV6e8t30k"};
	public static String[] wechatButtonInfo3List = {"CpJlAUkrFja6edvm_4Ma-v4-tUB6AHC8i_LungUbkyE"};
	public static String[] wechatButtonInfo4List = {"VuqeZPliv8_ZZGzyOnOinI7s95RC9b3E5FTjFr5OCHY"};
	public static String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
}
