package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface PuluoReservation {

	String getUserId(String idreservation);
	
	String setUserId(String idreservation, String iduser);
	
	String getEventId(String idreservation);
	
	String setEventId(String idreservation, String idevent);
	
	int getStatus(String idreservation);
	
	String setStatus(String idreservation, int idevent);
	
	Date getDate(String idreservation);
	
	String setDate(String idreservation, Date date);
	
	Time getTime(String idreservation);
	
	String setTime(String idreservation, Time time);
	
	String findReservationId(String iduser, String idevent, int status, Date date, Time time);
}
