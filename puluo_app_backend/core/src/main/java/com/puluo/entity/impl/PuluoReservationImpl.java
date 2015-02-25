package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoReservation;

public class PuluoReservationImpl implements PuluoReservation {

	private String iduser;
	private String idevent;
	private int status;
	private DateTime res_time;
	private String idreservation;

	public PuluoReservationImpl() {
	}

	public PuluoReservationImpl(String iduser, String idevent, int status,
			DateTime res_time, String idreservation) {
		this.iduser = iduser;
		this.idevent = idevent;
		this.status = status;
		this.res_time = res_time;
		this.idreservation = idreservation;
	}

	@Override
	public String iduser() {
		// TODO Auto-generated method stub
		return iduser;
	}

	@Override
	public String idevent() {
		// TODO Auto-generated method stub
		return idevent;
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
		return idreservation;
	}

}
