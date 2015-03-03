package com.puluo.entity.payment;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.impl.OrderEventType;

public interface OrderEvent {
	
	public String eventUUID();
	public PuluoPaymentOrder order();
	public OrderEventType eventType();
	public DateTime createdAt();
	
}
