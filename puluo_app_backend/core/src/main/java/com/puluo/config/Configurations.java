package com.puluo.config;

import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.enumeration.PuluoEnvironment;
import com.puluo.util.Strs;
import com.puluo.weichat.WechatKey;

public class Configurations {
	private static String wechatToken;
	private static String wechatAppId;
	private static String wechatAppKey;
	private static String webServer;

	public static final String puluoLocationName = "普罗总部";
	public static final PuluoEventLocation puluoLocation = DaoApi.getInstance()
			.eventLocationDao().getEventLocationByName(puluoLocationName);
	public static final PuluoEnvironment env = PuluoEnvironment
			.getEnvironment();
	public static final long orderIDBase = 10000;
	public static final boolean enableSMSNotification = true;
	private static final String imageServer = "http://img.puluosports.com/";

	public static final String[] wechatButtonInfo1List = { "8kNscO0VuUHZEnKmy46tDHzNyxbeSJOqKrGNq_ghS-M" };
	public static final String[] wechatButtonInfo2List = { "-vZuTzpVFraYz4ukhBe1XukSdvKCejArfUxbKyVS-rg" };
	public static final String[] wechatButtonInfo3List = { "2aPPBwl7ReUS6nO_0Ug5wVIuSOw9zvReYcY1Xlwe_4o" };
	public static final String[] wechatButtonInfo4List = { "sUNdqgGPNPRmvHmgUDklvAHTZV3BhSLjAIbw_uN5-GQ" };
	public static final String[] wechatButtonInfo5List = { "lnI6lmKccENtVoJ4Ine_dnSvaKURrLMGg6NFYcG9Q2s" };
	public static final String wechatCurriculum = "CpJlAUkrFja6edvm_4Ma-jkp5sRCffgm0yQRXBwSab4";
	public static final String emptyImage = Strs.join(imageServer, "empty.jpg");

	public static final Double minPrice = 0.0;
	public static final Double registrationAwardAmount = 50.0;

	static {
		switch (env) {
		case PROD:
			webServer = "puluosports.com";
			wechatToken = "puluosports";
			wechatAppId = "wx5d9079331e9af5e3";
			wechatAppKey = "b213b7abb4a2e6f8708cc9567f056d25";
			break;
		default:
			webServer = "183.131.76.93";
			wechatToken = "puluosports";
			wechatAppId = "wx61309c68eec99d0a";
			wechatAppKey = "55466cee5f714f61e8a05476c50cccfb";
			break;
		}
	}

	public static String webServer() {
		return webServer;
	}

	public static WechatKey wechatKey() {
		return new WechatKey(wechatAppKey, wechatAppId);
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

	public static String imgHttpLink(String name) {
		return  imgHttpLink(name,"");
	}
	public static String imgHttpLink(String name, String format) {
		if (Strs.isEmpty(name))
			return "";
		return Strs.join(Configurations.imageServer, name, format);
	}

	public static void main(String[] args) {
		System.out.println(puluoLocation.locationId());
	}

}
