package com.puluo.entity;

import org.joda.time.DateTime;


public interface PuluoReservation { // TODO fix me

	String idreservation();
	DateTime resTime();
	int status();
	String iduser();
	String idevent();
}
