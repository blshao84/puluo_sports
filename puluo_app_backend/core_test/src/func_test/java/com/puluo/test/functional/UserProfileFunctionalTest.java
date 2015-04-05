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
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class UserProfileFunctionalTest extends APIFunctionalTest {
	
	public static Log log = LogFactory.getLog(UserProfileFunctionalTest.class);
	
	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String mobile2 = "234567890";
	static String uuid2;
	static String password2 = "bcdefgh";
	static String mobile3 = "345678901";
	static String uuid3 = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
	static String sessionId;

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		dao.save(mobile1, password1);
		uuid1 = dao.getByMobile(mobile1).userUUID();
		dao.save(mobile2, password2);
		uuid2 = dao.getByMobile(mobile2).userUUID();
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
	}

	@Test
	public void testExistingMobile() {
		log.info("testExistingMobile start!");
		try {
			String session = login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/profile/" + mobile1, str);
			log.info(json);
			String uuid = super.getStringFromJson(json, "uuid");
			Assert.assertEquals("uuid should be " + uuid1, uuid1, uuid);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testExistingMobile done!");
	}

	@Test
	public void testExistingUUID() {
		log.info("testExistingUUID start!");
		try {
			String session = login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/profile/" + uuid2, str);
			log.info(json);
			String uuid = super.getStringFromJson(json, "uuid");
			Assert.assertEquals("uuid should be " + uuid2, uuid2, uuid);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testExistingUUID done!");
	}

	@Test
	public void testNotExistingMobile() {
		log.info("testNotExistingMobile start!");
		try {
			String session = login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/profile/" + mobile3, str);
			log.info(json);
			String error = super.getStringFromJson(json, "id");
			log.info("fail to login, error code = " + error);
			Assert.assertEquals("error id should be 17", "17", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testNotExistingMobile done!");
	}

	@Test
	public void testNotExistingUUID() {
		log.info("testNotExistingUUID start!");
		try {
			String session = login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/profile/" + uuid3, str);
			log.info(json);
			String error = super.getStringFromJson(json, "id");
			log.info("fail to login, error code = " + error);
			Assert.assertEquals("error id should be 17", "17", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testNotExistingUUID done!");
	}

	private String login() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			String session = getSessionID(json2);
			log.info("successfully aquired session:" + session);
			return session;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}
}
