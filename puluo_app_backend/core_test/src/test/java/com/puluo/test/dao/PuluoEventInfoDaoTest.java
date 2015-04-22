package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.impl.PuluoEventInfoImpl;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEventInfoDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoEventInfoDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoEventInfoDao infoDao = DaoTestApi.eventInfoDevDao;
		infoDao.createTable();
		PuluoEventInfo info = new PuluoEventInfoImpl("event_info_uuid_0", "name_0", "description_0",
				"coach_name_0", "coach_uuid_0", "thumbnail_uuid_0", "details_0", 0, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info);
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.eventInfoDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testUpsertEventInfo() {
		log.info("testUpsertEventInfo start!");
		PuluoEventInfo info1 = new PuluoEventInfoImpl("event_info_uuid_0", null, null,
				null, null, null, null, 1, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		boolean success1 = DaoTestApi.eventInfoDevDao.upsertEventInfo(info1);
		Assert.assertTrue("updating an info should be successful!", success1);
		PuluoEventInfo info2 = new PuluoEventInfoImpl("event_info_uuid_1", null, null,
				null, null, null, null, 1, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		boolean success2 = DaoTestApi.eventInfoDevDao.upsertEventInfo(info2);
		Assert.assertTrue("inserting an info should be successful!", success2);
		log.info("testUpsertEventInfo done!");
	}

	@Test 
	public void testGetEventPoster() {
		log.info("testGetEventPoster start!");
		PuluoEventInfoDao infoDao = DaoTestApi.eventInfoDevDao;
		PuluoEventInfo info1 = new PuluoEventInfoImpl("event_info_uuid_0", "name_0", "description_0",
				null, null, null, "details_0", 2, PuluoEventLevel.Level1, PuluoEventCategory.Others);
		infoDao.upsertEventInfo(info1);
		PuluoEventInfo info2 = infoDao.getEventInfoByUUID("event_info_uuid_0");
		Assert.assertEquals("name should be name_0!", "name_0" , info2.name());
		Assert.assertEquals("description should be description_0!", "description_0" , info2.description());
		Assert.assertEquals("coach name should be coach_name_0!", "coach_name_0" , info2.coachName());
		Assert.assertEquals("coach uuid should be coach_uuid_0!", "coach_uuid_0" , info2.coachUUID());
		Assert.assertEquals("thumbnail uuid should be thumbnail_uuid_0!", "thumbnail_uuid_0" , info2.coachThumbnail());
		Assert.assertEquals("details should be details_0!", "details_0" , info2.details());
		log.info("testGetEventPoster done!");
	}
}
