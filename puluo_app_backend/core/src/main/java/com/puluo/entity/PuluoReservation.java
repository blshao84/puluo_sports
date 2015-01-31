package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoReservation {

	String idreservation();
	Date res_date();
	Time res_time();
	int status();
	String iduser();
	String idevent();
}
