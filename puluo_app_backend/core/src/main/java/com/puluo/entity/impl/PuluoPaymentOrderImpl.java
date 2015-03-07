package com.puluo.entity.impl;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.PuluoOrderStatus;

public class PuluoPaymentOrderImpl implements PuluoPaymentOrder {

	// order number id is from the database
	private long order_num_id;
	private String order_uuid;

	/**
	 * external payment id
	 */
	private String payment_id;
	private double amount;
	private DateTime payment_time;
	private String user_id;
	private String event_id;
	private PuluoOrderStatus status;

	/**
	 * We don't save this member
	 */
	private PuluoDSI dsi;

	public PuluoPaymentOrderImpl() {
	}

	public PuluoPaymentOrderImpl(String idpayment, double amount,
			DateTime pay_time, String iduser, String idevent,
			PuluoOrderStatus status) {
		this(idpayment, amount, pay_time, iduser, idevent, status, DaoApi
				.getInstance());
	}

	public PuluoPaymentOrderImpl(String idpayment, double amount,
			DateTime pay_time, String iduser, String idevent,
			PuluoOrderStatus status, PuluoDSI dsi) {
		this.order_uuid = UUID.randomUUID().toString();
		this.payment_id = idpayment;
		this.amount = amount;
		this.payment_time = pay_time;
		this.user_id = iduser;
		this.event_id = idevent;
		this.status = status;
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
	public double amount() {
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

	@Override
	public String orderUUID() {
		return order_uuid;
	}

	@Override
	public long orderNumericID() {
		// TODO Auto-generated method stub
		return order_num_id;
	}

	@Override
	public boolean hasNumericID() {
		return order_num_id!=0L;
	}

}
