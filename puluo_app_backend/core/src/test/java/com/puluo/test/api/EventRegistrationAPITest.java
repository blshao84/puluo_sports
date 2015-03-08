package com.puluo.test.api;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import com.puluo.api.event.EventRegistrationAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoOrderEventDaoImpl;
import com.puluo.dao.impl.PuluoPaymentDaoImpl;

public class EventRegistrationAPITest {
	private final PuluoPaymentDao mockPaymentDao = Mockito
			.mock(PuluoPaymentDaoImpl.class);

	private final PuluoEventDao mockEventDao = Mockito.mock(PuluoEventDaoImpl.class);

	private final PuluoOrderEventDao mockOrderEventDao = Mockito
			.mock(PuluoOrderEventDaoImpl.class);
	
	@Test
	public void testCreateOrderWithoutEvent(){
		String eventUUID = "1";
		Mockito.when(mockEventDao.getEventByUUID(eventUUID)).thenReturn(null);
		Mockito.when(mockPaymentDao.getOrderByEvent(eventUUID)).thenReturn(null);
		EventRegistrationAPI api = new EventRegistrationAPI(eventUUID,"1");
		api.execute();
		
		ApiErrorResult expectedError = new ApiErrorResult("支付错误", String.format(
				"Event不存在(uuid is %s)", eventUUID), "");
		String expectedJsonResult = 
			"{\"id\":\"支付错误\","
			+ "\"message\":\"Event不存在(uuid is 1)\","
			+ "\"url\":\"\"}";
		Assert.assertEquals("rawResult expected to be null", expectedJsonResult, api.result());
		Assert.assertEquals("error result are not expected", expectedError, api.error);
	}
}
