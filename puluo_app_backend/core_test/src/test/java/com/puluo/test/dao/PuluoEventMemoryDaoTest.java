package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.impl.PuluoEventMemoryImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoEventMemoryDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoEventMemoryDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoEventMemoryDao memoryDao = DaoTestApi.eventMemoryDevDao;
		memoryDao.createTable();
		PuluoEventMemory memory = new PuluoEventMemoryImpl("uuid_0",
				"image_url_0", "event_uuid_0", "user_uuid_0", "timeline_uuid_0");
		memoryDao.upsertEventMemory(memory);
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.eventMemoryDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testUpsertEventMemory() {
		log.info("testUpsertEventMemory start!");
		boolean success1 = DaoTestApi.eventMemoryDevDao
				.upsertEventMemory(new PuluoEventMemoryImpl("uuid_0",
						null, "event_uuid_0", "user_uuid_0", "timeline_uuid_0"));
		Assert.assertTrue("updating a memory should be successful!", success1);
		boolean success2 = DaoTestApi.eventMemoryDevDao
				.upsertEventMemory(new PuluoEventMemoryImpl("uuid_1",
						null, "event_uuid_1", "user_uuid_1", "timeline_uuid_1"));
		Assert.assertTrue("inserting a memory should be successful!", success2);
		log.info("testUpsertEventMemory done!");
	}

	@Test 
	public void testGetEventMemoryByUUID() {
		log.info("testGetEventMemoryByUUID start!");
		PuluoEventMemoryDao memoryDao = DaoTestApi.eventMemoryDevDao;
		DaoTestApi.eventMemoryDevDao.upsertEventMemory(new PuluoEventMemoryImpl("uuid_2",
				null, "event_uuid_0", "user_uuid_0", "timeline_uuid_0"));
		DaoTestApi.eventMemoryDevDao.upsertEventMemory(new PuluoEventMemoryImpl("uuid_3",
				"image_url_3", "event_uuid_0", "user_uuid_0", "timeline_uuid_0"));
		List<PuluoEventMemory> memories = memoryDao.getEventMemoryByEventUUID("event_uuid_0");
		Assert.assertEquals("memories' size should be 3!", 3, memories.size());
		log.info("testGetEventMemoryByUUID done!");
	}
}
