package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoPayment { // TODO fix me

	String idPayment();
	float amount();
	int status();
	DateTime paymentTime();
	String idUser();
	String idEvent();
}
