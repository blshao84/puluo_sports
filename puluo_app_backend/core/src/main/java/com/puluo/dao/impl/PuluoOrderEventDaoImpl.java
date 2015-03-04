package com.puluo.dao.impl;

import java.util.List;

import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.jdbc.DalTemplate;

public class PuluoOrderEventDaoImpl extends DalTemplate implements
		PuluoOrderEventDao {

	@Override
	public List<OrderEvent> getOrderEvents(String paymentUUID) {
		// TODO Auto-generated method stub
		return null;
	}

}
