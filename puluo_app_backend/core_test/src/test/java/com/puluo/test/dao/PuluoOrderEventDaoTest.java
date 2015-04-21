package com.puluo.test.dao;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.dao.impl.PuluoOrderEventDaoImpl;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.entity.payment.OrderEvent;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.enumeration.OrderEventType;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoOrderEventDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoOrderEventDaoTest.class);

	private static String order_uuid_1;
	private static String order_uuid_2 = UUID.randomUUID().toString();

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder paymentOrder = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.01,
				DateTime.now(), UUID.randomUUID().toString(), UUID.randomUUID().toString(),
				PuluoOrderStatus.Undefined);
		paymentDao.createTable();
		paymentDao.upsertOrder(paymentOrder);
		order_uuid_1 = paymentOrder.orderUUID();
		PuluoOrderEventDaoImpl orderEventDao = (PuluoOrderEventDaoImpl) DaoTestApi.orderEventDevDao;
		orderEventDao.createTable();
		OrderEvent order_1 = new OrderEventImpl(order_uuid_1, OrderEventType.PlaceOrderEvent);
		OrderEvent order_2 = new OrderEventImpl(order_uuid_1, OrderEventType.PlaceOrderEvent);
		orderEventDao.saveOrderEvent(order_1, DaoTestApi.getInstance());
		orderEventDao.saveOrderEvent(order_2, DaoTestApi.getInstance());
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.paymentDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());dao = (DalTemplate) DaoTestApi.orderEventDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testGetOrderEventsByOrderUUID() {
		log.info("testGetOrderEventsByOrderUUID start!");
		PuluoOrderEventDao orderEventDao = DaoTestApi.orderEventDevDao;
		List<OrderEvent> events_1 = orderEventDao.getOrderEventsByOrderUUID(order_uuid_1);
		Assert.assertEquals("there should be 2 events with the order uuid of " + order_uuid_1, 2 , events_1.size());
		List<OrderEvent> events_2 = orderEventDao.getOrderEventsByOrderUUID(order_uuid_2);
		Assert.assertEquals("there should be 0 event with the order uuid of " + order_uuid_2, 0 , events_2.size());
		log.info("testGetOrderEventsByOrderUUID done!");
	}
}
