package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoSession;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoSessionDaoTest {
	public static Log log = LogFactory.getLog(PuluoSessionDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoSessionDao sessionDao = DaoTestApi.sessionDevDao;
		sessionDao.createTable();
		sessionDao.save("17721014665", "123456");
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.sessionDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test 
	public void testQuerySessionBySessionID() {
		log.info("testQuerySessionBySessionID start!");
		PuluoSession session = DaoTestApi.sessionDevDao.getBySessionID("123456");
		Assert.assertNotNull(session);
		Assert.assertEquals("session's user_uuid should be 17721014665", "17721014665", session.userMobile());
		log.info(session.userMobile());
		log.info("testQuerySessionBySessionID done!");
	}

	@Test
	public void testDeleteSessionBySessionID() {
		log.info("testDeleteSessionBySessionID start!");
		boolean success = DaoTestApi.sessionDevDao.deleteSession("123456");
		Assert.assertTrue("delete a session should be done!", success);
		PuluoSession session = DaoTestApi.sessionDevDao.getBySessionID("123456");
		Assert.assertNotNull(session);
		Assert.assertNotNull(session.deletedAt());
		log.info(session.deletedAt().toString());
		log.info("testDeleteSessionBySessionID done!");
	}
}
