package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoRegistrationInvitation {
	public String uuid();
	public String fromUUID();
	public String toUUID();
	//coupon awarded to fromUUID
	public String couponUUID();
	public DateTime createdAt();
	public DateTime updatedAt();
}
