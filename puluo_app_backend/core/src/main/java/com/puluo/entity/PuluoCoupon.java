package com.puluo.entity;

import com.puluo.enumeration.CouponType;

public interface PuluoCoupon {
	public String uuid();
	public CouponType couponType();
	public Double amount();
	public String ownerUUID();
	public boolean isValid();
}
