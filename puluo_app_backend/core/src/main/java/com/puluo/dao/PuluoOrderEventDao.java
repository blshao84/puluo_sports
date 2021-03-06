package com.puluo.dao;

import java.util.List;

import com.puluo.entity.payment.OrderEvent;

public interface PuluoOrderEventDao {
	public boolean createTable();
	public List<OrderEvent> getOrderEventsByOrderUUID(String orderUUID); 
	public boolean saveOrderEvent(OrderEvent event);
}
