package com.puluo.test.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.api.event.EventPromotionFactory;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoEventDaoImpl;
import com.puluo.dao.impl.PuluoRegistrationInvitationDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.entity.impl.PuluoRegistrationInvitationImpl;
import com.puluo.enumeration.EventStatus;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Pair;
import com.puluo.util.Strs;

public class EventRegistrationCheckTest {
	private static Log log = LogFactory
			.getLog(EventRegistrationCheckTest.class);

	public static String event_uuid = "EventRegistrationCheckTest_EVENT";
	public static String user_uuid1;
	public static String user_uuid2;
	public static List<String> invitationIDs = new ArrayList<String>();
	public static PuluoDSI dsi = DaoApi.getInstance();

	@BeforeClass
	public static void setupDB() {
		dsi.userDao().save("1234", "");
		dsi.userDao().save("5678", "");
		user_uuid1 = dsi.userDao().getByMobile("1234").userUUID();
		user_uuid2 = dsi.userDao().getByMobile("5678").userUUID();
		dsi.eventDao().saveEvent(new PuluoEventImpl(event_uuid));
		for (int i = 0; i < 5; i++) {
			String uuid = UUID.randomUUID().toString();
			dsi.invitationDao().insertInvitation(
					new PuluoRegistrationInvitationImpl(uuid, user_uuid1, Strs
							.join("invitation", i), null, DateTime.now(),
							DateTime.now()));

			invitationIDs.add(uuid);
		}
		for (int i = 0; i < 4; i++) {
			String uuid = UUID.randomUUID().toString();
			dsi.invitationDao().insertInvitation(
					new PuluoRegistrationInvitationImpl(uuid, user_uuid2, Strs
							.join("invitation", i), null, DateTime.now(),
							DateTime.now()));
			invitationIDs.add(uuid);
		}

	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) dsi.userDao();
		PuluoRegistrationInvitationDaoImpl invitationDao = 
				(PuluoRegistrationInvitationDaoImpl) dsi.invitationDao();
		PuluoEventDaoImpl eventDao = (PuluoEventDaoImpl)dsi.eventDao();
		eventDao.deleteByEventUUID(event_uuid);
		userDao.deleteByUserUUID(user_uuid1);
		userDao.deleteByUserUUID(user_uuid2);
		for(String id:invitationIDs){
			invitationDao.deleteByUUID(id);
		}
	}
	
	@Test
	public void testEventReferalCountCheck(){
		PuluoEvent event = dsi.eventDao().getEventByUUID(event_uuid);
		PuluoUser user1 = dsi.userDao().getByUUID(user_uuid1);
		PuluoUser user2 = dsi.userDao().getByUUID(user_uuid2);
		Pair<String, Boolean> res = null;
		res = EventPromotionFactory.checkReferalCount(event, user1);
		Assert.assertTrue(res.second);
		res = EventPromotionFactory.checkReferalCount(event, user2);
		Assert.assertTrue(res.second);
		res = EventPromotionFactory.checkReferalCount(null, null);
		Assert.assertFalse(res.second);
		dsi.eventDao().updateEvent(new PuluoEventImpl(
				event_uuid, DateTime.now(), EventStatus.Open, 0, 30, 50.0, 50.0,"","",3));
		event = dsi.eventDao().getEventByUUID(event_uuid);
		res = EventPromotionFactory.checkReferalCount(event, user1);
		Assert.assertTrue(res.second);
		res = EventPromotionFactory.checkReferalCount(event, user2);
		Assert.assertFalse(res.second);
	}
}
