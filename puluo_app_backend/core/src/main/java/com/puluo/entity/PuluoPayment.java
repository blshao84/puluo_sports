package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPayment { // TODO fix me

	String idPayment();
	float amount();
	int status();
	Date paymentDate();
	Time paymentTime();
	String idUser();
	String idEvent();
}
