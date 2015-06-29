package com.puluo.test.dao;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.enumeration.PuluoOrderStatus;
import com.puluo.enumeration.PuluoPartner;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoPaymentDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoPaymentDaoTest.class);

	private static String order_uuid_1;
	private static String order_uuid_2;
	private static String event_id_1 = UUID.randomUUID().toString();
	private static String event_id_2 = UUID.randomUUID().toString();
	private static String user_uuid = UUID.randomUUID().toString();

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder payment_order_1 = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.01,
				DateTime.now(), UUID.randomUUID().toString(), event_id_1, PuluoOrderStatus.Undefined,PuluoPartner.PuluoWeb);
		PuluoPaymentOrder payment_order_2 = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.02,
				DateTime.now(), UUID.randomUUID().toString(), event_id_1, PuluoOrderStatus.Undefined,PuluoPartner.PuluoWeb);
		PuluoPaymentOrder payment_order_3 = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.03,
				DateTime.now(), user_uuid, event_id_2, PuluoOrderStatus.Paid,PuluoPartner.PuluoWeb);
		PuluoPaymentOrder payment_order_4 = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.04,
				DateTime.now(), UUID.randomUUID().toString(), event_id_2, PuluoOrderStatus.Undefined,PuluoPartner.PuluoWeb);
		paymentDao.createTable();
		paymentDao.saveOrder(payment_order_1);
		paymentDao.saveOrder(payment_order_2);
		paymentDao.saveOrder(payment_order_3);
		paymentDao.saveOrder(payment_order_4);
		order_uuid_1 = payment_order_1.orderUUID();
		order_uuid_2 = payment_order_2.orderUUID();
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.paymentDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testGetOrderByUUID() {
		log.info("testGetOrderByUUID start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder paymentOrder = paymentDao.getOrderByUUID(order_uuid_1);
		Assert.assertTrue("the amount of the order should be 0.01", 0.01==paymentOrder.amount());
		log.info("testGetOrderByUUID done!");
	}

	@Test
	public void testGetOrderByEvent1() {
		log.info("testGetOrderByEvent1 start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		List<PuluoPaymentOrder> paymentOrders = paymentDao.getOrderByEvent(event_id_2);
		Assert.assertTrue("the size of the orders should be 2", 2==paymentOrders.size());
		Assert.assertTrue("the amount of the order 1 should be 0.03", 0.03==paymentOrders.get(0).amount());
		Assert.assertTrue("the amount of the order 2 should be 0.04", 0.04==paymentOrders.get(1).amount());
		log.info("testGetOrderByEvent1 done!");
	}

	@Test
	public void testGetOrderByEvent2() {
		log.info("testGetOrderByEvent2 start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder paymentOrder = paymentDao.getOrderByEvent(event_id_2, user_uuid);
		Assert.assertTrue("the amount of the order should be 0.03", 0.03==paymentOrder.amount());
		log.info("testGetOrderByEvent2 done!");
	}

	@Test
	public void testUpdateOrderStatus() {
		log.info("testUpdateOrderStatus start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder paymentOrder = paymentDao.getOrderByUUID(order_uuid_1);
		paymentDao.updateOrderStatus(paymentOrder, PuluoOrderStatus.Paying);
		paymentOrder = paymentDao.getOrderByUUID(order_uuid_1);
		Assert.assertEquals("the status of the order should be Paying", PuluoOrderStatus.Paying, paymentOrder.status());
		log.info("testUpdateOrderStatus done!");
	}

	@Test
	public void testSaveDuplicateOrder() {
		log.info("testSaveDuplicateOrder start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder payment_order = new PuluoPaymentOrderImpl(UUID.randomUUID().toString(), 0.03,
				DateTime.now(), user_uuid, event_id_2, PuluoOrderStatus.Paying,PuluoPartner.PuluoWeb);
		boolean fail = paymentDao.saveOrder(payment_order);
		Assert.assertFalse("saving a dupplicate order should be failed", fail);
		log.info("testSaveDuplicateOrder done!");
	}

	@Test
	public void testUpdateOrder() {
		log.info("testUpdateOrder start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder payment_order_1 = paymentDao.getOrderByUUID(order_uuid_2);
		String paymentId = UUID.randomUUID().toString();
		String userID = UUID.randomUUID().toString();
		String eventID = UUID.randomUUID().toString();
		PuluoPaymentOrder payment_order_2 = new PuluoPaymentOrderImpl(payment_order_1.orderNumericID(), payment_order_1.orderUUID(), paymentId, 0.03,
				DateTime.now().minusMinutes(1), userID, eventID, PuluoOrderStatus.Paying,PuluoPartner.PuluoWeb);
		paymentDao.updateOrder(payment_order_2);
		PuluoPaymentOrder payment_order_3 = paymentDao.getOrderByUUID(order_uuid_2);
		Assert.assertEquals("the payment id should be " + paymentId, paymentId, payment_order_3.paymentId());
		Assert.assertTrue("the amount should be 0.03", 0.03==payment_order_3.amount());
		Assert.assertNotEquals("the payment time should be changed", payment_order_1.paymentTime(), payment_order_3.paymentTime());
		Assert.assertEquals("the user id should be " + userID, userID, payment_order_3.userId());
		Assert.assertEquals("the event id should be " + eventID, eventID, payment_order_3.eventId());
		Assert.assertEquals("the status should be Paying", PuluoOrderStatus.Paying, payment_order_3.status());
		log.info("testUpdateOrder done!");
	}

	@Test
	public void testGetOrderByNumericID() {
		log.info("testGetOrderByNumericID start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder payment_order_1 = paymentDao.getOrderByUUID(order_uuid_1);
		PuluoPaymentOrder payment_order_2 = paymentDao.getOrderByNumericID(payment_order_1.orderNumericID());
		Assert.assertEquals("the uuid should be " + order_uuid_1, order_uuid_1, payment_order_2.orderUUID());
		log.info("testGetOrderByNumericID done!");
	}

	@Test
	public void testUpdateOrderPaymentInfo() {
		log.info("testUpdateOrderPaymentInfo start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		PuluoPaymentOrder paymentOrder = paymentDao.getOrderByUUID(order_uuid_1);
		String paymentId = UUID.randomUUID().toString();
		paymentDao.updateOrderPaymentInfo(paymentOrder, paymentId);
		paymentOrder = paymentDao.getOrderByUUID(order_uuid_1);
		Assert.assertEquals("the payment id should be " + paymentId, paymentId, paymentOrder.paymentId());
		log.info("testUpdateOrderPaymentInfo done!");
	}

	@Test
	public void testGetPaidOrdersByUserUUID() {
		log.info("testGetPaidOrdersByUserUUID start!");
		PuluoPaymentDao paymentDao = DaoTestApi.paymentDevDao;
		List<PuluoPaymentOrder> paymentOrders = paymentDao.getPaidOrdersByUserUUID(user_uuid);
		Assert.assertEquals("the size should be 1", 1, paymentOrders.size());
		Assert.assertEquals("the event uuid should be " + event_id_2, event_id_2, paymentOrders.get(0).eventId());
		Assert.assertTrue("the event uuid should be 0.03", 0.03==paymentOrders.get(0).amount());
		log.info("testGetPaidOrdersByUserUUID done!");
	}
}
