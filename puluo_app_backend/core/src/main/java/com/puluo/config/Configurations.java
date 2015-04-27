package com.puluo.config;

import com.puluo.weichat.WechatKey;

public class Configurations {
	public static long orderIDBase = 10000;
	public static String webServer = "https://www.puluosports.com";
	public static boolean enableSMSNotification = true;
	public static String imageServer ="http://puluodev.b0.upaiyun.com/";

	public static String wechatToken = "puluosports";
	public static String wechatAppId = "wx5d9079331e9af5e3";
	public static String wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
	public static WechatKey wechatKey = new WechatKey(wechatAppKey,wechatAppId);
	
	public static String[] wechatButtonInfo1List = {"B4IW1yc4SgTA3Svs9vmqTKcAG0Lva4dwRi-LWyYW9lw"};
	public static String[] wechatButtonInfo2List = {"B4IW1yc4SgTA3Svs9vmqTFsm3LE32Cs_cmjnS23yKjI"};
	public static String[] wechatButtonInfo3List = {"B4IW1yc4SgTA3Svs9vmqTB8fKy0zWa72pjVD7BhFsbs"};
	public static String[] wechatButtonInfo4List = {"B4IW1yc4SgTA3Svs9vmqTA0T1abAxv5DzV93xgoxpVE"};
	public static String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
}
