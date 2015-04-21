package com.puluo.test.functional;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoOrderEventDaoImpl;
import com.puluo.dao.impl.PuluoPaymentDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class EventRegisterFunctionalTest extends APIFunctionalTest {
	
	public static Log log = LogFactory.getLog(EventRegisterFunctionalTest.class);

	private static String location_uuid = UUID.randomUUID().toString();
	private static String info_uuid = UUID.randomUUID().toString();
	private static String event_uuid = UUID.randomUUID().toString();
	private static String mobile = "17721014665";
	private static String password = "123456";
	private static String order_uuid = "0";

	@BeforeClass
	public static void setupDB() {
		DaoApi.getInstance().userDao().save(mobile, password);
		PuluoEventLocation location = new PuluoEventLocationImpl(location_uuid,
				"浦东新区浦东南路978号东园三村331号", "200000", "东泰大楼南楼", "18521564305", "上海", 1.0, 1.0, 0, 15, 0);
		DaoApi.getInstance().eventLocationDao().saveEventLocation(location);
		PuluoEventInfo info = new PuluoEventInfoImpl(info_uuid, "臀部炸弹",
				"瘦臀、减脂", "James Bond", "007", "", "", 60, 1, 0);
		DaoApi.getInstance().eventInfoDao().saveEventInfo(info);
		PuluoEvent event = new PuluoEventImpl(event_uuid, TimeUtils.parseDateTime("2015-06-01 00:00:00"),
				EventStatus.Open, 3, 15, 0.0, 0.0, info_uuid, location_uuid, 0);
		DaoApi.getInstance().eventDao().saveEvent(event);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoUser user = userDao.getByMobile(mobile);
		if (user != null) {
			userDao.deleteByUserUUID(user.userUUID());
		}
		PuluoEventDaoImpl eventDao = (PuluoEventDaoImpl) DaoApi.getInstance().eventDao();
		eventDao.deleteByEventUUID(event_uuid);
		PuluoPaymentDaoImpl paymentDao = (PuluoPaymentDaoImpl) DaoApi.getInstance().paymentDao();
		paymentDao.deleteByOrderUUID(order_uuid);
		PuluoOrderEventDaoImpl orderEventDao = (PuluoOrderEventDaoImpl) DaoApi.getInstance().orderEventDao();
		orderEventDao.deleteByOrderUUID(order_uuid);
	}

	@Test
	public void testOrderWithZero() {
		super.runAuthenticatedTest(new EventRegisterFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				log.info("testOrderWithZero start!");
				JsonNode json = callAPI("/events/payment/" + event_uuid, inputs(session));
				log.info(json);
				getOrderUUID(json);
				String paid = getPaid(json);
				Assert.assertTrue("paid should be true", Boolean.valueOf(paid));
				log.info("testOrderWithZero done!");
			}

			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session, event_uuid);
			}
			
		});
	}
	
	abstract class EventRegisterFunctionalTestRunner implements PuluoAuthenticatedFunctionalTestRunner {
		@Override
		public String mobile() {
			return mobile;
		}

		@Override
		public String password() {
			return password;
		}
	}
	
	private String getPaid(JsonNode json) {
		return super.getStringFromJson(json, "paid");
	}
	
	private void getOrderUUID(JsonNode json) {
		order_uuid = super.getStringFromJson(json, "order_uuid");
	}
}
