package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoReservation;


public class PuluoReservationImpl implements PuluoReservation {

	private String iduser;
	private String idevent;
	private int status;
	private Date res_date;
	private Time res_time;
	private String idreservation;
	
	
	public PuluoReservationImpl() {}
	
	public PuluoReservationImpl(String iduser, String idevent, int status,
			Date res_date, Time res_time, String idreservation) {
		this.iduser = iduser;
		this.idevent = idevent;
		this.status = status;
		this.res_date = res_date;
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
	public Date res_date() {
		// TODO Auto-generated method stub
		return res_date;
	}

	@Override
	public Time res_time() {
		// TODO Auto-generated method stub
		return res_time;
	}

	@Override
	public String idreservation() {
		// TODO Auto-generated method stub
		return idreservation;
	}

	protected String getIdUser() {
		return iduser;
	}

	public void setIdUser(String iduser) {
		this.iduser = iduser;
	}

	protected String getIdEvent() {
		return idevent;
	}

	public void setIdEvent(String idevent) {
		this.idevent = idevent;
	}

	protected int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	protected Date getResDate() {
		return res_date;
	}

	public void setResDate(Date res_date) {
		this.res_date = res_date;
	}

	protected Time getResTime() {
		return res_time;
	}

	public void setResTime(Time res_time) {
		this.res_time = res_time;
	}

	protected String getIdReservation() {
		return idreservation;
	}

	public void setIdReservation(String idreservation) {
		this.idreservation = idreservation;
	}
}
