package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoAuthCodeRecord {
	public String userMobile();
	public String authCode();
	public PuluoAuthCodeType authType();
	public DateTime createdAt();
	public DateTime updatedAt();
}
