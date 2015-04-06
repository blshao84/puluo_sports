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
	static String password1 = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password1);
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
	public void testLoginToken() {
		log.info("testLoginToken start!");
		try {
			String session = super.login(mobile1, password1);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strStatus = String.format("{\"token\":\"%s\"}", session);
			JsonNode jsonStatus = callAPI("users/status", strStatus);
			log.info(jsonStatus);
			String status = super.getStringFromJson(jsonStatus, "login");
			Assert.assertEquals("login should be true", "true", status);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testLoginToken done!");
	}
}
