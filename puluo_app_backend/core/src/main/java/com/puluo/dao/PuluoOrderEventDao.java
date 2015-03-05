package com.puluo.dao;

import java.util.List;

import com.puluo.entity.payment.OrderEvent;

public interface PuluoOrderEventDao {
	public List<OrderEvent> getOrderEvents(String paymentUUID); 
	public boolean saveOrderEvent(OrderEvent event);
}
