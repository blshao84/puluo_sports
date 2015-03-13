package com.puluo.entity;

import org.joda.time.DateTime;

import com.puluo.entity.impl.PuluoAuthCodeType;

public interface PuluoAuthCodeRecord {
	public String userMobile();
	public String authCode();
	public PuluoAuthCodeType authType();
	public DateTime createdAt();
	public DateTime updatedAt();
}
