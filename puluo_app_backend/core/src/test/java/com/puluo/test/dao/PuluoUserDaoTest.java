package com.puluo.test.dao;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoUserDaoTest {
	public static Log log = LogFactory.getLog(PuluoUserDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save("1234567890", "1234");
	}

	@BeforeClass
	public static void cleanUpDB() {
		DalTemplate dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped tables " + dao.getFullTableName());

	}

	@Test
	public void testSaveDupUser() {
		boolean success = DaoTestApi.userDevDao.save("1234567890", "1234");
		Assert.assertFalse("save a duplicate user should fail!", success);
	}

	/*
	 * uncomment the following tests when getByMobile is implemented
	 * 
	 * @Test public void testQueryUserByMobile() { PuluoUser user =
	 * DaoTestApi.userDevDao.getByMobile("1234567890");
	 * Assert.assertNotNull(user);
	 * Assert.assertEquals("user's mobile should be 1234567890", "1234567890",
	 * user.mobile()); }
	 * 
	 * @Test public void testQueryUserByUUID() { PuluoUser user =
	 * DaoTestApi.userDevDao.getByMobile("1234567890"); String uuidString =
	 * user.userUUID(); PuluoUser user2 =
	 * DaoTestApi.userDevDao.getByUUID(uuidString); Assert.assertNotNull(user2);
	 * Assert.assertEquals("user's mobile should be 1234567890", "1234567890",
	 * user2.mobile()); }
	 */
}
