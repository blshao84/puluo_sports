package com.puluo.entity;

import java.util.List;

public interface PuluoAccount {
	public String accountUUID();
	public String ownerUUID();
	public PuluoUser owner();
	public Double balance();
	public Integer credit();
	public boolean deposit(Double amount);
	public boolean withdraw(Double amount);
	public boolean upgrade(Integer amount);
	public boolean downgrade(Integer amount);
	public List<String> couponUUIDs();
	public List<PuluoCoupon> coupons();
}
