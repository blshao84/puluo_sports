package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
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

public class LogoutFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(LogoutFunctionalTest.class);
	static String mobile1 = "123456789";
	static String password1 = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password1);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoUser user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
	}

	@Test
	public void testSuccessfulLogout() {

		String session = login(mobile1,password1);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", session);
			try {
				JsonNode json2 = callAPI("users/logout", inputs);
				String uuid = super.getStringFromJson(json2, "uuid");
				PuluoUser user = DaoApi.getInstance().userDao()
						.getByMobile(mobile1);
				Assert.assertEquals(
						"uuid returned in json should match mobile's uuid",
						user.userUUID(), uuid);
			} catch (UnirestException e) {
				e.printStackTrace();
				Assert.fail("fail to logout");
			}
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));
		} else
			Assert.fail("fail to login");

	}

	@Test
	public void testWrongToken() {

		String session = login(mobile1,password1);
		if (session != null) {
			String inputs = String.format("{\"token\":\"%s\"}", "wsldsak;nvi");
			try {
				HttpResponse<String> response = callAPIWithRejectedResponse("users/logout", inputs);
				int error = response.getStatus();
				Assert.assertEquals("response should be rejected with 403",403, error);
			} catch (UnirestException e) {
				e.printStackTrace();
				Assert.fail("fail to logout");
			}
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));
		} else
			Assert.fail("fail to login");

	}

}
