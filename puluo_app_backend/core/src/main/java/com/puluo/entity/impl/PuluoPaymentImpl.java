package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoPayment;


public class PuluoPaymentImpl implements PuluoPayment {

	private String idpayment;
	private float amount;
	private int status;
	private Date pay_date;
	private Time pay_time;
	private String iduser;
	private String idevent;
	

	public PuluoPaymentImpl() {}
	
	public PuluoPaymentImpl(String idpayment, float amount, int status,
			Date pay_date, Time pay_time, String iduser, String idevent) {
		this.idpayment = idpayment;
		this.amount = amount;
		this.status = status;
		this.pay_date = pay_date;
		this.pay_time = pay_time;
		this.iduser = iduser;
		this.idevent = idevent;
	}
	
	@Override
	public String idPayment() {
		// TODO Auto-generated method stub
		return idpayment;
	}

	@Override
	public float amount() {
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public int status() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public Date paymentDate() {
		// TODO Auto-generated method stub
		return pay_date;
	}

	@Override
	public Time paymentTime() {
		// TODO Auto-generated method stub
		return pay_time;
	}

	@Override
	public String idUser() {
		// TODO Auto-generated method stub
		return iduser;
	}

	@Override
	public String idEvent() {
		// TODO Auto-generated method stub
		return idevent;
	}

	protected String getIdPayment() {
		return idpayment;
	}

	public void setIdPayment(String idpayment) {
		this.idpayment = idpayment;
	}

	protected float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	protected int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	protected Date getPayDate() {
		return pay_date;
	}

	public void setPayDate(Date pay_date) {
		this.pay_date = pay_date;
	}

	protected Time getPayTime() {
		return pay_time;
	}

	public void setPayTime(Time pay_time) {
		this.pay_time = pay_time;
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
}
