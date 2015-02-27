package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoReservation;

public class PuluoReservationImpl implements PuluoReservation {

	private String user_id;
	private String event_id;
	private int status;
	private DateTime res_time;
	private String reservation_id;

	public PuluoReservationImpl() {
	}

	public PuluoReservationImpl(String iduser, String idevent, int status,
			DateTime res_time, String idreservation) {
		this.user_id = iduser;
		this.event_id = idevent;
		this.status = status;
		this.res_time = res_time;
		this.reservation_id = idreservation;
	}

	@Override
	public String iduser() {
		// TODO Auto-generated method stub
		return user_id;
	}

	@Override
	public String idevent() {
		// TODO Auto-generated method stub
		return event_id;
	}

	@Override
	public int status() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public DateTime resTime() {
		// TODO Auto-generated method stub
		return res_time;
	}

	@Override
	public String idreservation() {
		// TODO Auto-generated method stub
		return reservation_id;
	}

}
