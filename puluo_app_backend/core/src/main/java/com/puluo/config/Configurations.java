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
	
	public static final String[] wechatButtonInfo1List = {"IMQeaQEwrJVtUoS1W_GkVYci8RrdN0kkTuqzH8oXwcI"};
	public static final String[] wechatButtonInfo2List = {"BS819dxlOQJuv7-NXwfmSYVGc0HnCZqw00I93W0Z8_U"};
	public static final String[] wechatButtonInfo3List = {"BS819dxlOQJuv7-NXwfmSYVGc0HnCZqw00I93W0Z8_U"};
	public static final String[] wechatButtonInfo4List = {"BS819dxlOQJuv7-NXwfmSYVGc0HnCZqw00I93W0Z8_U"};
	public static final String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
	public static String emptyImage = Strs.join(imageServer,"empty.jpg");
}
