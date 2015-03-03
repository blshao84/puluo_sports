package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.impl.PuluoOrderStatus;

public class PuluoPaymentOrderImpl implements PuluoPaymentOrder {

	private String payment_id;
	private float amount;
	private DateTime payment_time;
	private String user_id;
	private String event_id;

	public PuluoPaymentOrderImpl() {
	}

	public PuluoPaymentOrderImpl(String idpayment, float amount,
			DateTime pay_time, String iduser, String idevent) {
		this.payment_id = idpayment;
		this.amount = amount;
		this.payment_time = pay_time;
		this.user_id = iduser;
		this.event_id = idevent;
	}

	@Override
	public String paymentId() {
		// TODO Auto-generated method stub
		return payment_id;
	}

	/**
	 * order status should be computed by PuluoOrderEvent
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
		return payment_time;
	}

	@Override
	public String userId() {
		// TODO Auto-generated method stub
		return user_id;
	}

	@Override
	public String eventId() {
		// TODO Auto-generated method stub
		return event_id;
	}

}
