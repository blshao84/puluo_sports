package com.puluo.test.dao;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoUser;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class PuluoUserDaoTest {
	public static Log log = LogFactory.getLog(PuluoUserDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save("17721014665", "123456");
		userDao.save("18521564305", "123456");
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testSaveDupUser() {
		log.info("testSaveDupUser start!");
		boolean success = DaoTestApi.userDevDao.save("17721014665", "123456");
		Assert.assertFalse("save a duplicate user should fail!", success);
		log.info("testSaveDupUser done!");
	}

	@Test 
	public void testQueryUserByMobile() {
		log.info("testQueryUserByMobile start!");
		PuluoUser user = DaoTestApi.userDevDao.getByMobile("17721014665");
		Assert.assertNotNull(user);
		Assert.assertEquals("user's mobile should be 17721014665", "17721014665", user.mobile());
		log.info("testQueryUserByMobile done!");
	}

	@Test
	public void testQueryUserByUUID() {
		log.info("testQueryUserByUUID start!");
		PuluoUser user = DaoTestApi.userDevDao.getByMobile("17721014665");
		String uuidString = user.userUUID();
		PuluoUser user2 = DaoTestApi.userDevDao.getByUUID(uuidString);
		Assert.assertNotNull(user2);
		Assert.assertEquals("user's mobile should be 17721014665", "17721014665", user2.mobile());
		log.info("testQueryUserByUUID done!");
	}
	
	@Test
	public void testUpdatePassword() {
		log.info("testUpdatePassword start!");
		PuluoUser user = DaoTestApi.userDevDao.getByMobile("17721014665");
		boolean success = DaoTestApi.userDevDao.updatePassword(user, "654321");
		Assert.assertTrue("updating password should not fail!", success);
		user = DaoTestApi.userDevDao.getByMobile("17721014665");
		Assert.assertNotNull(user);
		Assert.assertEquals("user's password should be 654321", "654321", user.password());
		log.info("testUpdatePassword done!");
	}
	
	@Test
	public void testUpdateProfile() throws Exception {
		log.info("testUpdateProfile start!");
		PuluoUser user = DaoTestApi.userDevDao.getByMobile("17721014665");
		user = DaoTestApi.userDevDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				"1_large.jpg", "saying", "email", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		Assert.assertNotNull(user);
		Assert.assertEquals("user's first name should be LEI", "LEI", user.firstName());
		Assert.assertEquals("user's last name should be SHI", "SHI", user.lastName());
		Assert.assertEquals("user's thumbnail should be 1.jpg", "1.jpg", user.thumbnail());
		Assert.assertEquals("user's large image should be 1_large.jpg", "1_large.jpg", user.largeImage());
		Assert.assertEquals("user's saying should be saying", "saying", user.saying());
		Assert.assertEquals("user's email should be email", "email", user.email());
		Assert.assertEquals("user's sex should be M", "M", String.valueOf((user.sex())));
		Assert.assertEquals("user's birthday should be 1984-10-11", "1984-10-11", TimeUtils.formatBirthday(user.birthday()));
		Assert.assertEquals("user's country should be China", "China", user.country());
		Assert.assertEquals("user's state should be Liaoning", "Liaoning", user.state());
		Assert.assertEquals("user's city should be Dalian", "Dalian", user.city());
		Assert.assertEquals("user's zip should be 116000", "116000", user.zip());
		log.info("testUpdateProfile done!");
	}
	
	@Test
	public void testFindUser() {
		log.info("testFindUser start!");
		PuluoUser user = DaoTestApi.userDevDao.getByMobile("17721014665");
		Assert.assertNotNull(user);
		user = DaoTestApi.userDevDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				"1_large.jpg", "saying", "email", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		Assert.assertNotNull(user);
		user = DaoTestApi.userDevDao.getByMobile("18521564305");
		Assert.assertNotNull(user);
		user = DaoTestApi.userDevDao.updateProfile(user, "LEI", "SHI", "2.jpg",
				"2_large.jpg", "saying", "email", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		Assert.assertNotNull(user);
		ArrayList<PuluoUser> users = (ArrayList<PuluoUser>) DaoTestApi.userDevDao.findUser("LEI", "SHI", "email", null);
		Assert.assertEquals("users' size should be 2", 2, users.size());
		users = (ArrayList<PuluoUser>) DaoTestApi.userDevDao.findUser("LEI", "SHI", "email", "18521564305");
		Assert.assertEquals("users' size should be 1", 1, users.size());
		users = (ArrayList<PuluoUser>) DaoTestApi.userDevDao.findUser("LEI", "SHI", "email", "13262247972");
		Assert.assertEquals("users' size should be 0", 0, users.size());
		log.info("testFindUser done!");
	}
}
