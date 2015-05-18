package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoRegistrationInvitation;

public class PuluoRegistrationInvitationImpl implements PuluoRegistrationInvitation{
	private final String uuid;
	private final String from_user_uuid;
	private final String to_user_uuid;
	private final String coupon_uuid;
	private final DateTime created_at;
	private final DateTime updated_at;
	
	
	public PuluoRegistrationInvitationImpl(String uuid,String from_user_uuid,
			String to_user_uuid, String coupon_uuid,DateTime created_at,DateTime updated_at) {
		super();
		this.uuid = uuid;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.coupon_uuid = coupon_uuid;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Override
	public String fromUUID() {
		return from_user_uuid;
	}

	@Override
	public String toUUID() {
		return to_user_uuid;
	}

	@Override
	public String couponUUID() {
		return coupon_uuid;
	}

	@Override
	public String uuid() {
		return uuid;
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
