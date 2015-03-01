package com.puluo.entity;

import org.joda.time.DateTime;

import com.puluo.entity.payment.PuluoOrderStatus;


public interface PuluoPaymentOrder {

	String paymentId();
	float amount();
	PuluoOrderStatus status();
	DateTime paymentTime();
	String userId();
	String eventId();
}
