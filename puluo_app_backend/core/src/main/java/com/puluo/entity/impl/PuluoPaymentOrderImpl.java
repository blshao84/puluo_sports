package com.puluo.entity.impl;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.enumeration.PuluoPartner;

public class PuluoPaymentOrderImpl implements PuluoPaymentOrder {

	// order number id is from the database
	private final long order_num_id;
	private final String order_uuid;

	/**
	 * external payment id
	 */
	private final String payment_id;
	private final double amount;
	private final DateTime payment_time;
	private final String user_id;
	private final String event_id;
	private final PuluoOrderStatus status;
	private final PuluoPartner source;


	public PuluoPaymentOrderImpl(long order_num_id, String order_uuid, String idpayment, double amount,
			DateTime pay_time, String iduser, String idevent,
			PuluoOrderStatus status,PuluoPartner source) {
		this.order_num_id = order_num_id;
		this.order_uuid = order_uuid;
		this.payment_id = idpayment;
		this.amount = amount;
		this.payment_time = pay_time;
		this.user_id = iduser;
		this.event_id = idevent;
		this.status = status;
		this.source = source;
	}


	public PuluoPaymentOrderImpl(String idpayment, double amount,
			DateTime pay_time, String iduser, String idevent,
			PuluoOrderStatus status,PuluoPartner source) {
		this(0L, UUID.randomUUID().toString(), idpayment, 
				amount, pay_time, iduser, idevent, status,source);
	}

	@Override
	public String paymentId() {
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
		return status == null ? PuluoOrderStatus.Undefined : status;
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
		return events(DaoApi.getInstance());
	}

	public List<OrderEvent> events(PuluoDSI dsi) {
		return dsi.orderEventDao().getOrderEventsByOrderUUID(order_uuid);
	}

	@Override
	public String orderUUID() {
		return order_uuid;
	}

	@Override
	public long orderNumericID() {
		return order_num_id;
	}

	@Override
	public boolean hasNumericID() {
		return order_num_id!=0L;
	}


	@Override
	public PuluoPartner source() {
		return source;
	}

}
