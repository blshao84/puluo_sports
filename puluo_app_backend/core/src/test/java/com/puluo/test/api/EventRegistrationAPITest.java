package com.puluo.test.api;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.puluo.api.event.EventRegistrationAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.PuluoPostLikeDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
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

	private class MockDSI extends PuluoDSI {

		@Override
		public PuluoEventDao eventDao() {
			// TODO Auto-generated method stub
			return mockEventDao;
		}

		@Override
		public PuluoEventInfoDao eventInfoDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoEventLocationDao eventLocationDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoEventMemoryDao eventMemoryDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoEventPosterDao eventPosterDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoPaymentDao paymentDao() {
			// TODO Auto-generated method stub
			return mockPaymentDao;
		}

		@Override
		public PuluoPostCommentDao postCommentDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoPostDao postDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoPostLikeDao postLikeDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoPrivateMessageDao privateMessageDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoSessionDao sessionDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoUserDao userDao() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public PuluoUserFriendshipDao friendshipDao() {
			// TODO Auto-generated method stub
			return null;
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

		ApiErrorResult expectedError = new ApiErrorResult("支付错误",
				String.format("Event不存在(uuid is %s)", eventUUID), "");
		String expectedJsonResult = "{\"id\":\"支付错误\","
				+ "\"message\":\"Event不存在(uuid is 1)\"," + "\"url\":\"\"}";
		Assert.assertEquals("rawResult expected to be null",
				expectedJsonResult, api.result());
		Assert.assertEquals("error result are not expected", expectedError,
				api.error);
	}

	@Test
	public void testCreateOrderWithEvent() {
		String eventUUID = "1";
		PuluoEvent event = Mockito.mock(PuluoEventImpl.class);
		PuluoPaymentOrder order = Mockito.mock(PuluoPaymentOrderImpl.class);
		Mockito.when(order.amount()).thenReturn(50.0);
		Mockito.when(order.orderNumericID()).thenReturn(9999L);
		Mockito.when(order.status()).thenReturn(PuluoOrderStatus.Undefined);
		Mockito.when(mockEventDao.getEventByUUID(eventUUID)).thenReturn(event);
		Mockito.when(event.price()).thenReturn(50.0);
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
		EventRegistrationAPI api = new EventRegistrationAPI("1", "1", mockDsi);
		api.execute();
		String expectedPaymentLink = "https://mapi.alipay.com/gateway.do?_input_charset=utf-8&body=19999&notify_url=/payment/alipay/notify&out_trade_no=19999&partner=2088211443186240&payment_type=1&return_url=/payment/alipay/return&seller_email=dsjtdqxmt@163.com&service=create_direct_pay_by_user&sign=8ca5266c0ae4e6c16189c5f98694b984&sign_type=MD5&subject=19999&total_fee=50.0";
		Assert.assertTrue("generated link are not expected", api.result()
				.contains(expectedPaymentLink));
	}
}
