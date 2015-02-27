package com.puluo.entity;

import org.joda.time.DateTime;

import com.puluo.entity.payment.PuluoOrderStatus;


public interface PuluoPaymentOrder { // TODO fix me

	String idPayment();
	float amount();
	PuluoOrderStatus status();
	DateTime paymentTime();
	String idUser();
	String idEvent();
}
