package com.puluo.entity;

public interface PuluoWechatBinding {
	public PuluoUser user();
	public String mobile();
	public String openId();
	public boolean verified();
	public String authCode();
	
}
