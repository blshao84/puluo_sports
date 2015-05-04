package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserFriendship;
import com.puluo.entity.impl.PuluoFriendInfo;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoUserFriendshipDaoTest {
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
		userDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				 "saying", "email_1", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		userDao.save("18521564305", "123456");
		user = userDao.getByMobile("18521564305");
		uuid_2 = user.userUUID();
		userDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				 "saying", "email_2", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		userDao.save("13262247972", "123456");
		user = userDao.getByMobile("13262247972");
		uuid_3 = user.userUUID();
		userDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				 "saying", "email_3", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		userDao.save("15330820432", "123456");
		user = userDao.getByMobile("15330820432");
		uuid_4 = user.userUUID();
		userDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				"saying", "email_4", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		userDao.save("00000000000", "123456");
		user = userDao.getByMobile("00000000000");
		uuid_5 = user.userUUID();
		userDao.updateProfile(user, "LEI", "SHI", "1.jpg",
				"saying", "email_5", "M", "1984-10-11", "China",
				"Liaoning", "Dalian", "116000");
		
		PuluoUserFriendshipDao friendDao = DaoTestApi.friendDevDao;
		friendDao.createTable();
		friendDao.addOneFriend(uuid_1, uuid_2);
		friendDao.addOneFriend(uuid_1, uuid_3);
		friendDao.addOneFriend(uuid_2, uuid_3);
		friendDao.addOneFriend(uuid_3, uuid_4);
		friendDao.addOneFriend(uuid_3, uuid_5);

		log.info("----------------------------------------------");
		log.info("uuid_1: " + uuid_1);
		log.info("uuid_2: " + uuid_2);
		log.info("uuid_3: " + uuid_3);
		log.info("uuid_4: " + uuid_4);
		log.info("uuid_5: " + uuid_5);
		log.info("----------------------------------------------");
		
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		
		DalTemplate dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		
		dao = (DalTemplate) DaoTestApi.friendDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		
		log.info("cleanUpDB done!");
	}
	
	@Test
	public void testGetFriendListByUUID() {
		log.info("testGetFriendListByUUID start!");
		PuluoUserFriendshipDao friendDao = DaoTestApi.friendDevDao;
		PuluoUserFriendship friendship = friendDao.getFriendListByUUID(uuid_1);
		String user_uuid = friendship.userUUID();
		Assert.assertEquals("user's uuid should be " + uuid_1, uuid_1, user_uuid);
		friendship.setDSI(DaoTestApi.getInstance());
		List<PuluoFriendInfo> friends = friendship.friends();
		String[] expected = {uuid_2, uuid_3};
		String[] current = new String[2];
		for (int i=0; i<friends.size(); i++) {
			current[i] = friends.get(i).user_uuid;
		}
		Assert.assertArrayEquals("user1's friends should be " + expected, expected, current);
		log.info("testGetFriendListByUUID done!");
	}
	
	@Test
	public void testAddOneFriend() {
		log.info("testAddOneFriend start!");
		PuluoUserFriendshipDao friendDao = DaoTestApi.friendDevDao;
		friendDao.addOneFriend(uuid_2, uuid_4);
		PuluoUserFriendship friendship = friendDao.getFriendListByUUID(uuid_2);
		String user_uuid = friendship.userUUID();
		Assert.assertEquals("user2's uuid should be " + uuid_2, uuid_2, user_uuid);
		friendship.setDSI(DaoTestApi.getInstance());
		List<PuluoFriendInfo> friends = friendship.friends();
		String[] expected2 = {uuid_1, uuid_3, uuid_4};
		String[] current = new String[3];
		for (int i=0; i<friends.size(); i++) {
			current[i] = friends.get(i).user_uuid;
		}
		Assert.assertArrayEquals("user2's friends should be " + expected2, expected2, current);
		friendship = friendDao.getFriendListByUUID(uuid_4);
		user_uuid = friendship.userUUID();
		Assert.assertEquals("user4's uuid should be " + uuid_4, uuid_4, user_uuid);
		friendship.setDSI(DaoTestApi.getInstance());
		friends = friendship.friends();
		String[] expected4 = {uuid_3, uuid_2};
		current = new String[2];
		for (int i=0; i<friends.size(); i++) {
			current[i] = friends.get(i).user_uuid;
		}
		Assert.assertArrayEquals("user4's friends should be " + expected4, expected4, current);
		log.info("testAddOneFriend done!");
	}
	
	@Test
	public void testDeleteOneFriend() {
		log.info("testDeleteOneFriend start!");
		PuluoUserFriendshipDao friendDao = DaoTestApi.friendDevDao;
		friendDao.deleteOneFriend(uuid_5, uuid_3);
		PuluoUserFriendship friendship = friendDao.getFriendListByUUID(uuid_3);
		String user_uuid = friendship.userUUID();
		Assert.assertEquals("user3's uuid should be " + uuid_3, uuid_3, user_uuid);
		friendship.setDSI(DaoTestApi.getInstance());
		List<PuluoFriendInfo> friends = friendship.friends();
		String[] expected3 = {uuid_1, uuid_2, uuid_4};
		String[] current = new String[3];
		for (int i=0; i<friends.size(); i++) {
			current[i] = friends.get(i).user_uuid;
		}
		Assert.assertArrayEquals("user3's friends should be " + expected3, expected3, current);
		friendship = friendDao.getFriendListByUUID(uuid_5);
		user_uuid = friendship.userUUID();
		Assert.assertEquals("user5's uuid should be " + uuid_5, uuid_5, user_uuid);
		friendship.setDSI(DaoTestApi.getInstance());
		friends = friendship.friends();
		String[] expected5 = new String[3];
		current = new String[3];
		for (int i=0; i<friends.size(); i++) {
			current[i] = friends.get(i).user_uuid;
		}
		Assert.assertArrayEquals("user5's friends should be " + expected5, expected5, current);
		log.info("testDeleteOneFriend done!");
	}
	
	@Test
	public void testIsFriend() {
		log.info("testIsFriend start!");
		PuluoUserFriendshipDao friendDao = DaoTestApi.friendDevDao;
		boolean result = friendDao.isFriend(uuid_1, uuid_5);
		Assert.assertFalse("user1 and user5 should not be friend", result);
		result = friendDao.isFriend(uuid_1, uuid_2);
		Assert.assertTrue("user1 and user2 should be friend", result);
		log.info("testIsFriend done!");
	}
}
