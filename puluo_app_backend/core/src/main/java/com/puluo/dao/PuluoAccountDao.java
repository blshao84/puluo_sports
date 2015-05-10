package com.puluo.dao;

import com.puluo.entity.PuluoAccount;

public interface PuluoAccountDao {
	public boolean createTable();
	public PuluoAccount getByAccountUUID(String uuid);
	public PuluoAccount getByUserUUID(String user_uuid);
	public boolean insertAccount(PuluoAccount account);
	public boolean updateBalance(PuluoAccount account, Double newBalance);
	public boolean updateCredit(PuluoAccount account, Integer newCredit);
	public boolean addCoupon(PuluoAccount account,String newCoupon);
	public boolean removeCoupon(PuluoAccount account, String newCoupon);
	
}
