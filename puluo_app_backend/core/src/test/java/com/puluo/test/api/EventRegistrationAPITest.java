package com.puluo.test.api;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.puluo.api.event.EventRegistrationAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.MockTestDSI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoOrderEventDaoImpl;
import com.puluo.dao.impl.PuluoPaymentDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoPaymentOrderImpl;
import com.puluo.entity.payment.impl.OrderEventImpl;
import com.puluo.entity.payment.impl.PuluoOrderStatus;

public class EventRegistrationAPITest {
	private final PuluoPaymentDao mockPaymentDao = Mockito
			.mock(PuluoPaymentDaoImpl.class);

	private final PuluoEventDao mockEventDao = Mockito
			.mock(PuluoEventDaoImpl.class);

	private final PuluoOrderEventDao mockOrderEventDao = Mockito
			.mock(PuluoOrderEventDaoImpl.class);

	private final String eventUUID = "1";

	private class MockDSI extends MockTestDSI {

		@Override
		public PuluoEventDao eventDao() {
			// TODO Auto-generated method stub
			return mockEventDao;
		}

		@Override
		public PuluoPaymentDao paymentDao() {
			// TODO Auto-generated method stub
			return mockPaymentDao;
		}

		@Override
		public PuluoOrderEventDao orderEventDao() {
			// TODO Auto-generated method stub
			return mockOrderEventDao;
		}
	}

	private final PuluoDSI mockDsi = new MockDSI();

	@Test
	public void testCreateOrderWithoutEvent() {
		String eventUUID = "1";
		Mockito.when(mockEventDao.getEventByUUID(eventUUID)).thenReturn(null);
		Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID))
				.thenReturn(null);
		EventRegistrationAPI api = new EventRegistrationAPI(eventUUID, "1",
				mockDsi);
		api.execute();

		ApiErrorResult expectedError = ApiErrorResult.getError(1);
		String expectedJsonResult = "{\"id\":1,\"error_type\":\"系统支付错误\","
				+ "\"message\":\"Event不存在\"," + "\"url\":\"\"}";
		Assert.assertEquals("rawResult expected to be null",
				expectedJsonResult, api.result());
		Assert.assertEquals("error result are not expected", expectedError,
				api.error);
	}

	@Test
	public void testCreateOrderWithEvent() {
		PuluoEvent event = Mockito.mock(PuluoEventImpl.class);
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		setupDao(event, order, false);
		setupEvent(event);
		setupOrder(order, PuluoOrderStatus.Undefined);
		EventRegistrationAPI api = new EventRegistrationAPI("1", "1", mockDsi);
		api.execute();
		assertPaymentlink(api.result());
	}

	@Test
	public void testDifferentUserInOrderAndAPI() {
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		Mockito.when(order.userId()).thenReturn("2");
		Mockito.when(mockPaymentDao.getOrderByEvent(Matchers.anyString()))
				.thenReturn(order);
		EventRegistrationAPI api = new EventRegistrationAPI("1", "1", mockDsi);
		api.execute();
		String expectedErrorMessage = "{\"id\":3,\"error_type\":\"系统支付错误\",\"message\":\"订单中的用户id与该用户不匹配\",\"url\":\"\"}";
		Assert.assertEquals("unexpected error message", expectedErrorMessage,
				api.result());
	}

	@Test
	public void testCanceledOrder() {
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		setupOrder(order, PuluoOrderStatus.Cancel);
		Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID)).thenReturn(
				order);
		String actualErrorMessage = run();
		String expectedErrorMessage = "{\"id\":2,\"error_type\":\"系统支付错误\",\"message\":\"订单已取消\",\"url\":\"\"}";
		Assert.assertEquals("unexpected error message", expectedErrorMessage,
				actualErrorMessage);
	}

	@Test
	public void testPaidOrder() {
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		setupOrder(order, PuluoOrderStatus.Paid);
		Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID)).thenReturn(
				order);
		String actualResult = run();
		assertPaid(actualResult);
	}

	@Test
	public void testCompleteOrder() {
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		setupOrder(order, PuluoOrderStatus.Complete);
		Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID)).thenReturn(
				order);
		String actualResult = run();
		assertPaid(actualResult);
	}

	@Test
	public void testUndefinedOrder() {
		String actualResult = runUpdateWithStatus(PuluoOrderStatus.Undefined);
		assertPaymentlink(actualResult);
	}

	@Test
	public void testNewOrder() {
		String actualResult = runUpdateWithStatus(PuluoOrderStatus.New);
		assertPaymentlink(actualResult);
	}

	@Test
	public void testPayingOrder() {
		String actualResult = runUpdateWithStatus(PuluoOrderStatus.Paying);
		assertPaymentlink(actualResult);
	}

	private void assertPaid(String actualResult) {
		String expectedResult = "{\"link\":\"\",\"paid\":true}";
		Assert.assertEquals(
				"unexpected result: should return nothing because order is processed",
				expectedResult, actualResult);
	}

	private void assertPaymentlink(String actualResult) {
		String expectedPaymentLink = "https://mapi.alipay.com/gateway.do?_input_charset=utf-8&body=19999&notify_url=/payment/alipay/notify&out_trade_no=19999&partner=2088211443186240&payment_type=1&return_url=/payment/alipay/return&seller_email=dsjtdqxmt@163.com&service=create_direct_pay_by_user&sign=8ca5266c0ae4e6c16189c5f98694b984&sign_type=MD5&subject=19999&total_fee=50.0";
		Assert.assertTrue("generated link are not expected",
				actualResult.contains(expectedPaymentLink));
	}

	private String runUpdateWithStatus(PuluoOrderStatus status) {
		PuluoEvent event = Mockito.mock(PuluoEventImpl.class);
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		setupDao(event, order, true);
		setupEvent(event);
		setupOrder(order, status);
		return run();
	}

	private String run() {
		EventRegistrationAPI api = new EventRegistrationAPI("1", "1", mockDsi);
		api.execute();
		return api.result();
	}

	private void setupDao(PuluoEvent event, PuluoPaymentOrder order,
			boolean getOrderByEvent) {
		Mockito.when(mockEventDao.getEventByUUID(eventUUID)).thenReturn(event);
		if (getOrderByEvent) {
			Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID)).thenReturn(
					order);
		}
		Mockito.when(mockPaymentDao.getOrderByUUID(Matchers.anyString()))
				.thenReturn(order);
		Mockito.when(
				mockPaymentDao.saveOrder(Matchers
						.any(PuluoPaymentOrderImpl.class))).thenReturn(true);
		Mockito.when(
				mockOrderEventDao.saveOrderEvent(Matchers
						.any(OrderEventImpl.class))).thenReturn(true);

		Mockito.when(
				mockPaymentDao.updateOrderStatus(
						Matchers.any(PuluoPaymentOrderImpl.class),
						Matchers.any(PuluoOrderStatus.class))).thenReturn(true);
	}

	private void setupOrder(PuluoPaymentOrder order, PuluoOrderStatus status) {
		Mockito.when(order.userId()).thenReturn("1");
		Mockito.when(order.status()).thenReturn(status);
		Mockito.when(order.amount()).thenReturn(50.0);
		Mockito.when(order.orderNumericID()).thenReturn(9999L);

	}

	private void setupEvent(PuluoEvent event) {
		Mockito.when(event.price()).thenReturn(50.0);
	}
}
