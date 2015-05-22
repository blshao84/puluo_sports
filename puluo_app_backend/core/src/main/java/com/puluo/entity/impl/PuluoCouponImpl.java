package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoCoupon;
import com.puluo.enumeration.CouponType;

public class PuluoCouponImpl implements PuluoCoupon {
	public final String uuid;
	public final CouponType type;
	public final Double amount;
	public final String user_uuid;
	public final String order_uuid;
	public final String location_uuid;
	public final DateTime valid_until;
	
	public PuluoCouponImpl(String uuid, CouponType type, Double amount,
			String user_uuid,String order_uuid,String location_uuid,DateTime valid_until) {
		super();
		this.uuid = uuid;
		this.type = type;
		this.amount = amount;
		this.user_uuid = user_uuid;
		this.order_uuid = order_uuid;
		this.valid_until = valid_until;
		this.location_uuid = location_uuid;
	}

	@Override
	public String uuid() {
		return uuid;
	}

	@Override
	public CouponType couponType() {
		return type;
	}

	@Override
	public Double amount() {
		return amount;
	}

	@Override
	public String ownerUUID() {
		return user_uuid;
	}

	@Override
	public boolean isValid() {
		return valid_until.isAfterNow();
	}

	@Override
	public DateTime validUntil() {
		return valid_until;
	}

	@Override
	public String orderUUID() {
		return order_uuid;
	}

	@Override
	public String locationUUID() {
		return location_uuid;
	}

}
