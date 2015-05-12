package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoCoupon;

public interface PuluoCouponDao {
	public boolean createTable();
	//get coupon ignore 'valid'
	public PuluoCoupon getByCouponUUID(String uuid);
	public List<PuluoCoupon> getByUserUUID(String user_uuid);
	//get coupon based on id and validity
	public PuluoCoupon getByCouponUUID(String uuid, boolean is_valid);
	public List<PuluoCoupon> getByUserUUID(String user_uuid,boolean is_valid);
	public boolean insertCoupon(PuluoCoupon coupon);
	public boolean updateCoupon(PuluoCoupon coupon);
	
}
