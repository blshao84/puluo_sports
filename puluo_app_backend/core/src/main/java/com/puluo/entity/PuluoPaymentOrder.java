package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.PuluoOrderStatus;


public interface PuluoPaymentOrder {
	long orderNumericID();
	String orderUUID();
	String paymentId();
	float amount();
	PuluoOrderStatus status();
	DateTime paymentTime();
	String userId();
	String eventId();
	List<OrderEvent> events();
}
