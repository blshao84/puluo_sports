package com.puluo.entity.payment;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.enumeration.OrderEventType;

public interface OrderEvent {
	
	public String eventUUID();
	public PuluoPaymentOrder order(PuluoDSI dsi);
	public OrderEventType eventType();
	public DateTime createdAt();
	public String orderUUID();
	
}
