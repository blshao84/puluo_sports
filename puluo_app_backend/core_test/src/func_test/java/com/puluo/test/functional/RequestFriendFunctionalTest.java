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

public class RequestFriendFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(RequestFriendFunctionalTest.class);
	static String mobile1 = "123456789";
	static String mobile2 = "234567890";
	static String mobile3 = "345678901";
	static String mobile4 = "456789012";
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password);
		DaoApi.getInstance().userDao().save(mobile2, password);
		DaoApi.getInstance().userDao().save(mobile3, password);
		DaoApi.getInstance().userDao().save(mobile4, password);
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

	@Test
	public void testListFriendForMobile() {
		log.info("testListFriendForMobile start!");
		try {
			String strLogin = String.format("{\"password\":\"%s\",\"mobile\":\"%s\"}", password, mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strRequest = String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session, DaoApi.getInstance().userDao().getByMobile(mobile2).userUUID());
			JsonNode jsonRequest = callAPI("users/friends/request", strRequest);
			log.info(jsonRequest);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListFriendForMobile done!");
	}
}

