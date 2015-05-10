package com.puluo.entity.impl;

import java.util.ArrayList;
import java.util.List;

import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoAccount;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoAccountImpl implements PuluoAccount {
	private static Log log = LogFactory.getLog(PuluoAccountImpl.class);
	
	public final String uuid;
	public final String user_uuid;
	public final Double balance;
	public final Integer credit;
	public final List<String> coupon_uuids;

	public PuluoAccountImpl(String uuid, String user_uuid, Double balance,
			Integer credit, List<String> coupon_uuids) {
		super();
		this.uuid = uuid;
		this.user_uuid = user_uuid;
		this.balance = balance;
		this.credit = credit;
		this.coupon_uuids = coupon_uuids;
	}

	@Override
	public String accountUUID() {

		return uuid;
	}

	@Override
	public String ownerUUID() {

		return user_uuid;
	}

	@Override
	public PuluoUser owner() {

		return owner(DaoApi.getInstance());
	}

	public PuluoUser owner(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(user_uuid);
	}

	@Override
	public Double balance() {

		return balance;
	}

	@Override
	public Integer credit() {

		return credit;
	}

	@Override
	public boolean deposit(Double amount) {
		return deposit(amount, DaoApi.getInstance());
	}

	public boolean deposit(Double amount, PuluoDSI dsi) {
		if (amount < 0)
			return false;
		else {
			Double newBalance = balance + amount;
			return dsi.accountDao().updateBalance(this, newBalance);
		}
	}

	@Override
	public boolean withdraw(Double amount) {
		return withdraw(amount, DaoApi.getInstance());
	}

	public boolean withdraw(Double amount, PuluoDSI dsi) {
		if (amount < 0) {
			return false;
		} else if (amount > balance) {
			return false;
		} else {
			Double newBalance = balance - amount;
			return dsi.accountDao().updateBalance(this, newBalance);
		}
	}

	@Override
	public boolean upgrade(Integer amount) {
		return upgrade(amount, DaoApi.getInstance());
	}

	public boolean upgrade(Integer amount, PuluoDSI dsi) {
		if (amount < 0) {
			return false;
		} else {
			Integer newCredit = credit + amount;
			return dsi.accountDao().updateCredit(this, newCredit);
		}
	}

	@Override
	public boolean downgrade(Integer amount) {
		return downgrade(amount, DaoApi.getInstance());
	}

	public boolean downgrade(Integer amount, PuluoDSI dsi) {
		if (amount < 0) {
			return false;
		} else if (amount > credit) {
			return false;
		} else {
			Integer newCredit = credit - amount;
			return dsi.accountDao().updateCredit(this, newCredit);
		}
	}

	@Override
	public List<String> couponUUIDs() {

		return coupon_uuids;
	}

	@Override
	public List<PuluoCoupon> coupons() {
		return coupons(DaoApi.getInstance());
	}
	
	public List<PuluoCoupon> coupons(PuluoDSI dsi) {
		List<PuluoCoupon> coupons = new ArrayList<PuluoCoupon>();
		PuluoCouponDao dao = dsi.couponDao();
		for(String cid:coupon_uuids){
			PuluoCoupon c = dao.getByCouponUUID(cid);
			if(c!=null) coupons.add(c);
		}
		if(coupons.size()!=couponUUIDs().size()){
			log.warn("coupon_uuuid.size != coupons.size, some uuid are not valid");
		}
		return coupons;
	}

}
