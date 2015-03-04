package com.puluo.entity.payment.impl;

import org.joda.time.DateTime;

import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.payment.OrderEvent;

public class OrderEventImpl implements OrderEvent {
	private final String uuid;
	private final DateTime created_at;
	private final String order_uuid;
	private final OrderEventType type;
	
	
	
	public OrderEventImpl(String uuid, DateTime created_at, String order_uuid,
			OrderEventType type) {
		super();
		this.uuid = uuid;
		this.created_at = created_at;
		this.order_uuid = order_uuid;
		this.type = type;
	}

	@Override
	public String eventUUID() {
		return uuid;
	}

	@Override
	public PuluoPaymentOrder order() {
		return DaoApi.paymentDao.getOrderByUUID(order_uuid);
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
