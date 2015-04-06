package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class ListFriendFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(ListFriendFunctionalTest.class);
	static String mobile1 = "123456789";
	static String uuid1;
	static String mobile2 = "234567890";
	static String uuid2;
	static String mobile3 = "345678901";
	static String uuid3;
	static String mobile4 = "456789012";
	static String uuid4 = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		userDao.save(mobile1, password);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		userDao.save(mobile2, password);
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		userDao.save(mobile3, password);
		uuid3 = userDao.getByMobile(mobile3).userUUID();
		PuluoUserFriendshipDaoImpl friendshipDao = (PuluoUserFriendshipDaoImpl) DaoApi.getInstance().friendshipDao();
		friendshipDao.addOneFriend(uuid1, uuid2);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoUserFriendshipDaoImpl friendshipDao = (PuluoUserFriendshipDaoImpl) DaoApi.getInstance().friendshipDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile4);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
		}
	}
	
	private void testExistingMobileOrUUID(String name, String value, int size) {
		log.info(name + " start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/friends/" + value, str);
			log.info(json);
			JsonNode details = new JsonNode(super.getStringFromJson(json, "details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be " + size, size, details.getArray().length());
			for (int i=0; i<details.getArray().length(); i++) {
				String mobile = super.getStringFromJson(new JsonNode(String.valueOf(details.getArray().get(i))), "uuid");
				Assert.assertEquals("mobile should be " + uuid2, uuid2, mobile);
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}
	
	private void testNotExistingMobileOrUUID(String name, String value) {
		log.info(name + " start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/friends/" + value, str);
			log.info(json);
//			String error = super.getStringFromJson(json, "id");
//			Assert.assertEquals("error id should be 100", "100", error);
			JsonNode details = new JsonNode(super.getStringFromJson(json, "details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be 0", 0, details.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	@Test
	public void testExistingMobileWithFriends() {
		testExistingMobileOrUUID("testExistingMobileWithFriends", mobile1, 1);
	}

	@Test
	public void testExistingUUIDWithFriends() {
		testExistingMobileOrUUID("testExistingUUIDWithFriends", uuid1, 1);
	}

	@Test
	public void testExistingMobileWithoutFriends() {
		testExistingMobileOrUUID("testExistingMobileWithFriends", mobile3, 0);
	}

	@Test
	public void testExistingUUIDWithoutFriends() {
		testExistingMobileOrUUID("testExistingUUIDWithoutFriends", uuid3, 0);
	}

	@Test
	public void testNotExistingMobile() {
		testNotExistingMobileOrUUID("testNotExistingMobile", mobile4);
	}

	@Test
	public void testNotExistingUUID() {
		testNotExistingMobileOrUUID("testNotExistingUUID", uuid4);
	}
}
