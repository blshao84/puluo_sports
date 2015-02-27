package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.PuluoOrderStatus;

public class PuluoPaymentOrderImpl implements PuluoPaymentOrder {

	private String idpayment;
	private float amount;
	private DateTime pay_time;
	private String iduser;
	private String idevent;

	public PuluoPaymentOrderImpl() {
	}

	public PuluoPaymentOrderImpl(String idpayment, float amount,
			DateTime pay_time, String iduser, String idevent) {
		this.idpayment = idpayment;
		this.amount = amount;
		this.pay_time = pay_time;
		this.iduser = iduser;
		this.idevent = idevent;
	}

	@Override
	public String idPayment() {
		// TODO Auto-generated method stub
		return idpayment;
	}

	/**
	 * order status should be computed by all PuluoOrderEvent
	 */
	@Override
	public float amount() {
		// TODO Auto-generated method stub
		return amount;
	}

	@Override
	public PuluoOrderStatus status() {
		// TODO Auto-generated method stub
		return PuluoOrderStatus.Undefined;
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
