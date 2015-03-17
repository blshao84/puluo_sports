package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.impl.PuluoEventLocationImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEventLocationDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoEventLocationDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoEventLocationDao locationDao = DaoTestApi.eventLocationDevDao;
		locationDao.createTable();
		PuluoEventLocation location = new PuluoEventLocationImpl("uuid_0",
				"address_0", "zip_0", "name_0", "phone_0", "city_0",
				"longitude_0", "lattitude_0", 0, 0, 0);
		locationDao.upsertEventLocation(location);
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.eventLocationDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testUpsertEventLocation() {
		log.info("testUpsertEventLocation start!");
		boolean success1 = DaoTestApi.eventLocationDevDao
				.upsertEventLocation(new PuluoEventLocationImpl("uuid_0",
						"address_0", "zip_0", "name_0", "phone_0", "city_0",
						null, null, 0, 0, 0));
		Assert.assertTrue("updating a location should be successful!", success1);
		boolean success2 = DaoTestApi.eventLocationDevDao
				.upsertEventLocation(new PuluoEventLocationImpl("uuid_1",
						"address_1", "zip_1", "name_1", "phone_1", "city_1",
						null, null, 1, 1, 1));
		Assert.assertTrue("inserting a location should be successful!", success2);
		log.info("testUpsertEventLocation done!");
	}

	@Test 
	public void testGetEventLocationByUUID() {
		log.info("testGetEventLocationByUUID start!");
		PuluoEventLocationDao locationDao = DaoTestApi.eventLocationDevDao;
		locationDao.upsertEventLocation(new PuluoEventLocationImpl("uuid_2",
				"address_2", "zip_2", "name_2", "phone_2", "city_2",
				"longitude_0", "lattitude_0", 0, 0, 0));
		locationDao.upsertEventLocation(new PuluoEventLocationImpl("uuid_2",
				"address_2", "zip_2", "name_2", "phone_2", "city_2",
				null, null, 2, 2, 2));
		PuluoEventLocation location = locationDao.getEventLocationByUUID("uuid_2");
		Assert.assertEquals("address should be address_2!", "address_2" , location.address());
		Assert.assertEquals("longitude should be null!", null , location.longitude());
		Assert.assertEquals("capacity should be 2!", 2 , location.capacity());
		log.info("testGetEventLocationByUUID done!");
	}
}
