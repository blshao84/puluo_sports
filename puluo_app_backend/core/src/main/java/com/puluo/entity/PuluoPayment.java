package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoPayment {

	float getAmount(String idpayment);
	
	String setAmount(String idpayment, float amount);
	
	int getStatus(String idpayment);
	
	String setStatus(String idpayment, int status);
	
	Date getDate(String idpayment);
	
	String setDate(String idpayment, Date date);
	
	Time getTime(String idpayment);
	
	String setTime(String idpayment, Time time);
	
	String getUser(String idpayment);
	
	String setUser(String idpayment, String iduser);
	
	String getEvent(String idpayment);
	
	String setEvent(String idpayment, String idevent);
	
	String findPaymentId(String iduser, String idevent, Date date, Time time, int status, float amount);
}
