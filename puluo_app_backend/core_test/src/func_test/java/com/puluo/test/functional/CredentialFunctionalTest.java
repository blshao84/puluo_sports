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

public class CredentialFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(CredentialFunctionalTest.class);
	static String mobile1 = "123456789";
	static String mobile2 = "234567890";
	static String password1 = "abcdefg";
	static String password2 = "bcdefgh";
	static String password3 = "cdefghi";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password1);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoUser user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
	}

	@Test
	public void testIncorrectMobile() { // mobile存在，但password不匹配
		log.info("testIncorrectMobile start!");
		try {
			String strLogin = String.format("{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strUpdate = String.format("{\"password\":\"%s\",\"new_password\":\"%s\",\"token\":\"%s\"}", password2, password2, session);
			JsonNode jsonUpdate = callAPI("users/credential/update", strUpdate);
			String error = super.getStringFromJson(jsonUpdate, "id");
			log.info("fail to login, error code = " + error);
			Assert.assertEquals("error id should be 19", "19", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testIncorrectMobile done!");
	}

	@Test
	public void testMissingNewPassword() { // mobile存在，password匹配，但缺少new_password
		log.info("testMissingNewPassword start!");
		try {
			String strLogin = String.format("{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strUpdate = String.format("{\"password\":\"%s\",\"token\":\"%s\"}", password1, session);
			JsonNode jsonUpdate = callAPI("users/credential/update", strUpdate);
			String error = super.getStringFromJson(jsonUpdate, "id");
			log.info("fail to login, error code = " + error);
			Assert.assertEquals("error id should be 15", "15", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testMissingNewPassword done!");
	}

	@Test
	public void testUpdate() { // mobile存在，password匹配，new_password正确更新
		log.info("testUpdate start!");
		try {
			String strLogin = String.format("{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String strUpdate = String.format("{\"password\":\"%s\",\"new_password\":\"%s\",\"token\":\"%s\"}", password1, password3, session);
			JsonNode jsonUpdate = callAPI("users/credential/update", strUpdate);
			log.info("password :" + super.getStringFromJson(jsonUpdate, "password"));
			Assert.assertTrue("password should be " + password1, password1.equals(this.getStringFromJson(jsonUpdate, "password")));
			log.info("new_password :" + super.getStringFromJson(jsonUpdate, "new_password"));
			Assert.assertTrue("new_password should be " + password3, password3.equals(this.getStringFromJson(jsonUpdate, "new_password")));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testUpdate done!");
	}
}
