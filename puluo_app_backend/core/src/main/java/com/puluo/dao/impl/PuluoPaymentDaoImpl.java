package com.puluo.dao.impl;

import com.puluo.dao.PuluoPaymentDao;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.impl.PuluoOrderStatus;
import com.puluo.jdbc.DalTemplate;

public class PuluoPaymentDaoImpl extends DalTemplate implements PuluoPaymentDao{

	@Override
	public PuluoPaymentOrder getOrderByUUID(String orderUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPaymentOrder getOrderByEvent(String eventUUID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateOrderStatus(PuluoPaymentOrder order,
			PuluoOrderStatus nextStatus) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveOrder(PuluoPaymentOrder order) {
		// TODO Auto-generated method stub
		return false;
	}

}
