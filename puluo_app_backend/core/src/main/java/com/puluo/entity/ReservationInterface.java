package com.puluo.entity;

public interface ReservationInterface {

	String getUserId(String idreservation);
	
	String setUserId(String idreservation, String iduser);
	
	String getEventId(String idreservation);
	
	String setEventId(String idreservation, String idevent);
	
	
}
