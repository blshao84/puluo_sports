package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.PuluoOrderStatus;

public class PuluoPaymentOrderImpl implements PuluoPaymentOrder {

	/**
	 * external payment id
	 */
	private String payment_id;
	private float amount;
	private DateTime payment_time;
	private String user_id;
	private String event_id;
	private PuluoDSI dsi;
	
	public PuluoPaymentOrderImpl() {
	}

	public PuluoPaymentOrderImpl(String idpayment, float amount,
			DateTime pay_time, String iduser, String idevent) {
		this.payment_id = idpayment;
		this.amount = amount;
		this.payment_time = pay_time;
		this.user_id = iduser;
		this.event_id = idevent;
		this.dsi = DaoApi.getInstance();
	}
	
	public PuluoPaymentOrderImpl(String idpayment, float amount,
			DateTime pay_time, String iduser, String idevent,PuluoDSI dsi) {
		this.payment_id = idpayment;
		this.amount = amount;
		this.payment_time = pay_time;
		this.user_id = iduser;
		this.event_id = idevent;
		this.dsi = dsi;
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
		return amount;
	}

	@Override
	public PuluoOrderStatus status() {
		return PuluoOrderStatus.Undefined;
	}

	@Override
	public DateTime paymentTime() {
		return payment_time;
	}

	@Override
	public String userId() {
		return user_id;
	}

	@Override
	public String eventId() {
		return event_id;
	}

	@Override
	public List<OrderEvent> events() {
		return dsi.orderEventDao().getOrderEvents(payment_id);
	}

}
