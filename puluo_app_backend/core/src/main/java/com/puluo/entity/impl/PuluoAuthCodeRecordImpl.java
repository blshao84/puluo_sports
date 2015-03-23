package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.entity.PuluoAuthCodeType;

public class PuluoAuthCodeRecordImpl implements PuluoAuthCodeRecord{
	private final String user_mobile;
	private final String auth_code;
	private final PuluoAuthCodeType auth_type;
	private final DateTime created_at;
	private final DateTime updated_at;
	
	


	public PuluoAuthCodeRecordImpl(String user_uuid, String auth_code,
			PuluoAuthCodeType auth_type, DateTime created_at, DateTime updated_at) {
		super();
		this.user_mobile = user_uuid;
		this.auth_code = auth_code;
		this.auth_type = auth_type;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Override
	public String userMobile() {
		
		return user_mobile;
	}

	@Override
	public String authCode() {
		
		return auth_code;
	}

	@Override
	public PuluoAuthCodeType authType() {
		
		return auth_type;
	}

	@Override
	public DateTime createdAt() {
		
		return created_at;
	}
	
	@Override
	public DateTime updatedAt() {
		
		return updated_at;
	}

}
