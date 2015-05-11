package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.config.Configurations;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class UserUpdateFunctionalTest extends APIFunctionalTest {
	
	public static Log log = LogFactory.getLog(UserUpdateFunctionalTest.class);
	
	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String sessionId;

	private String login() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
		try {
			JsonNode json = callAPI("users/login", inputs);
			sessionId = getSessionID(json);
			log.info("successfully aquired session:" + sessionId);
			return sessionId;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void test(String name, String key, String value, String info, boolean isThumbnail) {
		log.info(name + " start!");
		try {
			login();
			String str = String.format("{\"token\":\"%s\", \"" + key + "\":\"%s\"}", sessionId, value);
			JsonNode json = callAPI("users/update", str);
			log.info(json);
			JsonNode public_info = new JsonNode(super.getStringFromJson(json, info));
			String expectedValue = null;
			if(isThumbnail) expectedValue = Strs.join(Configurations.imageServer,value,""); else expectedValue = value;
			Assert.assertEquals(key.replace("_", " ") + " should be " + value, expectedValue, super.getStringFromJson(public_info, key));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		dao.save(mobile1, password1);
		uuid1 = dao.getByMobile(mobile1).userUUID();
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
	public void testUpdateFirstName() {
		test("testUpdateFirstName", "first_name", "Lei", "public_info",false);
	}

	@Test
	public void testUpdateLastName() {
		test("testUpdateLastName", "last_name", "Shi", "public_info",false);
	}

	@Test
	public void testUpdateThumbnail() {
		test("testUpdateThumbnail", "thumbnail", "1234.jpg", "public_info",true);
	}

	@Test
	public void testUpdateSaying() {
		test("testUpdateSaying", "saying", "Hello, Puluo!", "public_info",false);
	}

	@Test
	public void testUpdateEmail() {
		test("testUpdateEmail", "email", "sinorockie@outlook.com", "private_info",false);
	}

	@Test
	public void testUpdateSex() {
		test("testUpdateSex", "sex", "M", "private_info",false);
	}

	@Test
	public void testUpdateBirthday() {
		test("testUpdateBirthday", "birthday", "1984-10-11", "private_info",false);
	}

	@Test
	public void testUpdateCountry() {
		test("testUpdateCountry", "country", "China", "private_info",false);
	}

	@Test
	public void testUpdateState() {
		test("testUpdateState", "state", "Shanghai", "private_info",false);
	}

	@Test
	public void testUpdateCity() {
		test("testUpdateCity", "city", "Pudong", "private_info",false);
	}

	@Test
	public void testUpdateZip() {
		test("testUpdateZip", "zip", "200000", "private_info",false);
	}
}
