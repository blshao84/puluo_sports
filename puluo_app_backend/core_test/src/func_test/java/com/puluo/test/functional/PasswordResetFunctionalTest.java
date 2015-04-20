package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class PasswordResetFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory
			.getLog(PasswordResetFunctionalTest.class);
	static String mobile1 = "123456789";
	static String password1 = "abcdefg";
	static String mobile2 = "987654321";
	static String password2 = "abcdefg";
	static String newPassword = "cdefghi";
	static String authCode = "123456";
	static String wrongAuthCode = "654321";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password1);
		DaoApi.getInstance().userDao().save(mobile2, password2);
		DaoApi.getInstance().authCodeRecordDao()
				.upsertPasswordResetAuthCode(mobile1, authCode);
	}

	@AfterClass
	public static void cleanupDB() {
		deleteUser(mobile1);
		deleteUser(mobile2);
	}

	private static void deleteUser(String mobile) {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl) DaoApi
				.getInstance().authCodeRecordDao();
		PuluoUser user = dao.getByMobile(mobile);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			authDao.deleteByMobile(mobile);
		}
	}

	@Test
	public void testMissingAuthCode() { // mobile存在，但authCode不存在
		log.info("testMissingAuthCode start!");
		try {
			String strLogin = String.format(
					"{\"password\":\"%s\",\"mobile\":\"%s\"}", password2,
					mobile2);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String strUpdate = String
					.format("{\"auth_code\":\"%s\",\"new_password\":\"%s\",\"token\":\"%s\"}",
							authCode, newPassword, session);
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
	public void testIncorrectMobile() { // mobile存在，但authCode不匹配
		log.info("testIncorrectMobile start!");
		try {
			String strLogin = String.format(
					"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1,
					mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String strUpdate = String
					.format("{\"auth_code\":\"%s\",\"new_password\":\"%s\",\"token\":\"%s\"}",
							wrongAuthCode, newPassword, session);
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
	public void testMissingNewPassword() { // mobile存在，authCode匹配，但缺少new_password
		log.info("testMissingNewPassword start!");
		try {
			String strLogin = String.format(
					"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1,
					mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String strUpdate = String.format(
					"{\"auth_code\":\"%s\",\"token\":\"%s\"}", authCode,
					session);
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
	public void testUpdate() { // mobile存在，auth_code匹配，new_password正确更新
		log.info("testUpdate start!");
		try {
			String strLogin = String.format(
					"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1,
					mobile1);
			JsonNode jsonLogin = callAPI("users/login", strLogin);
			String session = getSessionID(jsonLogin);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String strUpdate = String
					.format("{\"auth_code\":\"%s\",\"new_password\":\"%s\",\"token\":\"%s\"}",
							authCode, newPassword, session);
			JsonNode jsonUpdate = callAPI("users/credential/update", strUpdate);
			System.out.println(jsonUpdate);
			log.info("auth_code :"
					+ super.getStringFromJson(jsonUpdate, "auth_code"));
			Assert.assertTrue("auth_code should be " + authCode, authCode
					.equals(this.getStringFromJson(jsonUpdate, "auth_code")));
			log.info("new_password :"
					+ super.getStringFromJson(jsonUpdate, "new_password"));
			Assert.assertTrue("new_password should be " + newPassword,
					newPassword.equals(this.getStringFromJson(jsonUpdate,
							"new_password")));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testUpdate done!");
	}
}
