package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoFriendRequestDaoTest {
	public static Log log = LogFactory.getLog(PuluoFriendRequestDaoTest.class);

	public static String user1;
	public static String user2;
	public static String user3;

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save("31", "111111");
		userDao.save("32", "222222");
		userDao.save("33", "333333");
		user1 = userDao.getByMobile("31").userUUID();
		user2 = userDao.getByMobile("32").userUUID();
		user3 = userDao.getByMobile("33").userUUID();
		
		PuluoFriendRequestDao requestDao = DaoTestApi.friendRequestDevDao;
		requestDao.createTable();
		requestDao.saveNewRequest("123456", user1, user2);
		requestDao.saveNewRequest("234567", user2, user3);
		
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		
		DalTemplate userDao = (DalTemplate) DaoTestApi.userDevDao;
		userDao.getWriter().execute("drop table " + userDao.getFullTableName());
		log.info("dropped table " + userDao.getFullTableName());
		
		DalTemplate requestDao = (DalTemplate) DaoTestApi.friendRequestDevDao;
		requestDao.getWriter().execute("drop table " + requestDao.getFullTableName());
		log.info("dropped table " + requestDao.getFullTableName());
		
		log.info("cleanUpDB done!");
	}

	@Test
	public void testUpdateRequestStatus() {
		log.info("testUpdateRequestStatus start!");

		PuluoFriendRequestDao requestDao = DaoTestApi.friendRequestDevDao;
		requestDao.updateRequestStatus("123456", FriendRequestStatus.Denied);
		PuluoFriendRequest request = requestDao.findByUUID("123456");
		Assert.assertEquals("request's status should be denied", FriendRequestStatus.Denied, request.requestStatus());
		
		log.info("testUpdateRequestStatus done!");
	}

	@Test
	public void testGetFriendRequestByUsers() {
		log.info("testGetFriendRequestByUsers start!");

		PuluoFriendRequestDao requestDao = DaoTestApi.friendRequestDevDao;
		List<PuluoFriendRequest> requests = requestDao.getFriendRequestByUsers(user2, user3, FriendRequestStatus.Requested);
		Assert.assertEquals("request's uuid should be 234567", "234567", requests.get(0).requestUUID());
		
		log.info("testGetFriendRequestByUsers done!");
	}
}
