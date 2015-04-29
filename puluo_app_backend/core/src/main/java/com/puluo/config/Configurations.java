package com.puluo.config;

import com.puluo.weichat.WechatKey;

public class Configurations {
	public static final long orderIDBase = 10000;
	public static final String webServer = "www.puluosports.com";
	public static final boolean enableSMSNotification = true;
	public static final String imageServer ="http://puluodev.b0.upaiyun.com/";

	public static final String wechatToken = "puluosports";
	public static final String wechatAppId = "wx5d9079331e9af5e3";
	public static final String wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
	public static final WechatKey wechatKey = new WechatKey(wechatAppKey,wechatAppId);
	
	public static final String[] wechatButtonInfo1List = {"B4IW1yc4SgTA3Svs9vmqTKcAG0Lva4dwRi-LWyYW9lw"};
	public static final String[] wechatButtonInfo2List = {"B4IW1yc4SgTA3Svs9vmqTFsm3LE32Cs_cmjnS23yKjI"};
	public static final String[] wechatButtonInfo3List = {"B4IW1yc4SgTA3Svs9vmqTB8fKy0zWa72pjVD7BhFsbs"};
	public static final String[] wechatButtonInfo4List = {"B4IW1yc4SgTA3Svs9vmqTA0T1abAxv5DzV93xgoxpVE"};
	public static final String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
}
