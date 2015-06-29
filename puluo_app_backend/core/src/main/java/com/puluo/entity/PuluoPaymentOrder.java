package com.puluo.entity;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.entity.payment.OrderEvent;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.enumeration.PuluoPartner;


public interface PuluoPaymentOrder {
	long orderNumericID();
	String orderUUID();
	String paymentId();
	double amount();
	PuluoOrderStatus status();
	DateTime paymentTime();
	String userId();
	String eventId();
	List<OrderEvent> events();
	boolean hasNumericID();
	PuluoPartner source();
}
