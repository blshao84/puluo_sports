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

public class StatusFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(StatusFunctionalTest.class);
	static String mobile1 = "123456789";
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password);
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
	}

	@Test
	public void testLoginToken() { // token已登录
		log.info("testLoginToken start!");
		try {
			String session = login();
			log.info("successfully aquired session:" + session);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strStatus = String.format("{\"token\":\"%s\"}", session);
			JsonNode jsonStatus = callAPI("users/status", strStatus);
			log.info(jsonStatus);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testLoginToken done!");
	}

	private String login() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}", password, mobile1);
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
