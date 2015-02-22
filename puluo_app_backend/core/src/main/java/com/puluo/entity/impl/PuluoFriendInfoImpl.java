package com.puluo.entity.impl;

import com.puluo.entity.PuluoFriendInfo;

public class PuluoFriendInfoImpl implements PuluoFriendInfo {

	private final String user_uuid;
	private final String last_name;
	private final String first_name;
	private final String user_email;
	private final String user_mobile;
	
	
	
	public PuluoFriendInfoImpl(String user_uuid, String last_name,
			String first_name, String user_email, String user_mobile) {
		super();
		this.user_uuid = user_uuid;
		this.last_name = last_name;
		this.first_name = first_name;
		this.user_email = user_email;
		this.user_mobile = user_mobile;
	}

	@Override
	public String userUUID() {
		// TODO Auto-generated method stub
		return user_uuid;
	}

	@Override
	public String lastName() {
		// TODO Auto-generated method stub
		return last_name;
	}

	@Override
	public String firstName() {
		// TODO Auto-generated method stub
		return first_name;
	}

	@Override
	public String email() {
		// TODO Auto-generated method stub
		return user_email;
	}

	@Override
	public String mobile() {
		// TODO Auto-generated method stub
		return user_mobile;
	}

}
