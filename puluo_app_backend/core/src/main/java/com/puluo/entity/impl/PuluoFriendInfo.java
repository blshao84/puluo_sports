package com.puluo.entity.impl;


public class PuluoFriendInfo {

	public final String user_uuid;
	public final String last_name;
	public final String first_name;
	public final String user_email;
	public final String user_mobile;
	
	
	
	public PuluoFriendInfo(String user_uuid, String last_name,
			String first_name, String user_email, String user_mobile) {
		super();
		this.user_uuid = user_uuid;
		this.last_name = last_name;
		this.first_name = first_name;
		this.user_email = user_email;
		this.user_mobile = user_mobile;
	}
}
