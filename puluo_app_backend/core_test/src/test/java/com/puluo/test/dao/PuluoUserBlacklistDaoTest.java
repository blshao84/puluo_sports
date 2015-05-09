package com.puluo.test.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserBlacklist;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoUserBlacklistDaoTest {
	public static Log log = LogFactory.getLog(PuluoUserFriendshipDaoTest.class);

	private static String uuid_1;
	private static String uuid_2;
	private static String uuid_3;
	private static String uuid_4;
	private static String uuid_5;

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");

		PuluoUserDao userDao = DaoTestApi.userDevDao;
		PuluoUser user;
		userDao.createTable();
		userDao.save("17721014665", "123456");
		user = userDao.getByMobile("17721014665");
		uuid_1 = user.userUUID();

		userDao.save("18521564305", "123456");
		user = userDao.getByMobile("18521564305");
		uuid_2 = user.userUUID();

		userDao.save("13262247972", "123456");
		user = userDao.getByMobile("13262247972");
		uuid_3 = user.userUUID();

		userDao.save("15330820432", "123456");
		user = userDao.getByMobile("15330820432");
		uuid_4 = user.userUUID();

		userDao.save("00000000000", "123456");
		user = userDao.getByMobile("00000000000");
		uuid_5 = user.userUUID();

		PuluoUserBlacklistDao blacklistDao = DaoTestApi.blacklistDevDao;
		blacklistDao.createTable();

	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");

		DalTemplate dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());

		dao = (DalTemplate) DaoTestApi.blacklistDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());

		log.info("cleanUpDB done!");
	}

	@Test
	public void testBanUser() {
		PuluoUserBlacklistDao dao = DaoTestApi.getInstance().blacklistDao();
		PuluoUserBlacklist blacklist = dao.banUser(uuid_1, uuid_2);
		List<String> expected = new ArrayList<String>();
		expected.add(uuid_2);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		blacklist = dao.banUser(uuid_1, uuid_3);
		expected = new ArrayList<String>();
		expected.add(uuid_2);
		expected.add(uuid_3);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		blacklist = dao.banUser(uuid_1, uuid_3);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		blacklist = dao.getBlacklistByUUID(uuid_1);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		blacklist = dao.banUser(uuid_3, uuid_1);
		expected = new ArrayList<String>();
		expected.add(uuid_1);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		
	}
	
	@Test
	public void testFreeUser(){
		PuluoUserBlacklistDao dao = DaoTestApi.getInstance().blacklistDao();
		PuluoUserBlacklist blacklist = dao.banUser(uuid_2, uuid_1);
		blacklist = dao.banUser(uuid_2, uuid_3);
		List<String> expected;
		expected= new ArrayList<String>();
		expected.add(uuid_1);
		expected.add(uuid_3);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		blacklist = dao.freeUser(uuid_2, uuid_1);
		expected= new ArrayList<String>();
		expected.add(uuid_3);
		Assert.assertEquals(toSet(expected), toSet(blacklist.bannedUUIDs()));
		
	}
	
	@Test
	public void testIsBanned(){
		PuluoUserBlacklistDao dao = DaoTestApi.getInstance().blacklistDao();
		boolean status = dao.isBanned(uuid_4, uuid_5);
		Assert.assertFalse(status);
		status = dao.isBanned(uuid_5, uuid_4);
		Assert.assertFalse(status);
		dao.banUser(uuid_4, uuid_5);
		status = dao.isBanned(uuid_4, uuid_5);
		Assert.assertTrue(status);
		status = dao.isBanned(uuid_5, uuid_4);
		Assert.assertFalse(status);
		
	}
		
	private Set<String> toSet(List<String> ids) {
		Set<String> idSet = new HashSet<String>();
		for(String id:ids){
			idSet.add(id);
		}
		return idSet;
	}
}
