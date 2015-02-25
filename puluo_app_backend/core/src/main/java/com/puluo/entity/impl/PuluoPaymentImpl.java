package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoPayment;

public class PuluoPaymentImpl implements PuluoPayment {

	private String idpayment;
	private float amount;
	private int status;
	private DateTime pay_time;
	private String iduser;
	private String idevent;

	public PuluoPaymentImpl() {
	}

	public PuluoPaymentImpl(String idpayment, float amount, int status,
			DateTime pay_time, String iduser, String idevent) {
		this.idpayment = idpayment;
		this.amount = amount;
		this.status = status;
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
	public DateTime paymentTime() {
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

}
