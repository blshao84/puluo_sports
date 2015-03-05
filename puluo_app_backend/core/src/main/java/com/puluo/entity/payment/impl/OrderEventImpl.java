package com.puluo.entity.payment.impl;

import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;

public class OrderEventImpl implements OrderEvent {
	private final String uuid;
	private final DateTime created_at;
	private final String order_uuid;
	private final OrderEventType type;
	private final PuluoDSI dsi;
	
	
	
	public OrderEventImpl(String order_uuid,
			OrderEventType type) {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.created_at = DateTime.now();
		this.order_uuid = order_uuid;
		this.type = type;
		this.dsi = DaoApi.getInstance();
	}
	
	public OrderEventImpl(String uuid, DateTime created_at, String order_uuid,
			OrderEventType type,PuluoDSI dsi) {
		super();
		this.uuid = uuid;
		this.created_at = created_at;
		this.order_uuid = order_uuid;
		this.type = type;
		this.dsi = dsi;
	}

	@Override
	public String eventUUID() {
		return uuid;
	}

	@Override
	public PuluoPaymentOrder order() {
		return dsi.paymentDao().getOrderByUUID(order_uuid);
	}

	@Override
	public OrderEventType eventType() {
		return type;
	}

	@Override
	public DateTime createdAt() {
		return created_at;
	}

}
