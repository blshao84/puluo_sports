package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.enumeration.PuluoOrderStatus;

public interface PuluoPaymentDao {
	
	public boolean createTable();

	public PuluoPaymentOrder getOrderByUUID(String orderUUID);

	public List<PuluoPaymentOrder> getOrderByEvent(String eventUUID);

	public boolean updateOrderStatus(PuluoPaymentOrder order,
			PuluoOrderStatus nextStatus);

	public boolean upsertOrder(PuluoPaymentOrder order);

	public PuluoPaymentOrder getOrderByNumericID(long orderNumericID);

	public boolean updateOrderPaymentInfo(PuluoPaymentOrder order,
			String paymentRef);
	
	public PuluoPaymentOrder getOrderByEvent(String eventUUID, String userUUID);
}
