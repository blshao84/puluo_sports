package com.puluo.dao;

import com.puluo.entity.PuluoPaymentOrder;

public interface PuluoPaymentDao {
	
	public PuluoPaymentOrder getOrderByUUID(String orderUUID);

}
