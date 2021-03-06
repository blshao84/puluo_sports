package com.puluo.entity;

import org.joda.time.DateTime;

import com.puluo.enumeration.CouponType;

public interface PuluoCoupon {
	public String uuid();
	public CouponType couponType();
	public Double amount();
	public String ownerUUID();
	public String orderUUID();
	public boolean isValid();
	public DateTime validUntil();
	public String locationUUID();
}
