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

public class UserSearchFunctionalTest extends APIFunctionalTest {

	public static Log log = LogFactory.getLog(UserSearchFunctionalTest.class);

	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String mobile2 = "234567890";
	static String uuid2;
	static String password2 = "bcdefgh";
	static String mobile3 = "345678901";
	static String uuid3;
	static String password3 = "cdefghi";
	static String mobile4 = "456789012";
	static String uuid4;
	static String password4 = "defghij";
	static String sessionId;

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		dao.save(mobile1, password1);
		uuid1 = dao.getByMobile(mobile1).userUUID();
		dao.updateProfile(dao.getByMobile(mobile1), "Sports_1", "Puluo_1",
				null, null, null, "sinorockie@puluo_1.com", null, null, null,
				null, null, null);
		dao.save(mobile2, password2);
		uuid2 = dao.getByMobile(mobile2).userUUID();
		dao.updateProfile(dao.getByMobile(mobile2), "Sports_1", "Puluo_1",
				null, null, null, "sinorockie@puluo_2.com", null, null, null,
				null, null, null);
		dao.save(mobile3, password3);
		uuid3 = dao.getByMobile(mobile3).userUUID();
		dao.updateProfile(dao.getByMobile(mobile3), "Sports_2", "Puluo_1",
				null, null, null, "sinorockie@puluo_3.com", null, null, null,
				null, null, null);
		dao.save(mobile4, password4);
		uuid4 = dao.getByMobile(mobile4).userUUID();
		dao.updateProfile(dao.getByMobile(mobile4), "Sports_3", "Puluo_2",
				null, null, null, "sinorockie@puluo_1.com", null, null, null,
				null, null, null);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
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
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		user = dao.getByMobile(mobile4);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
	}

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

	private void testSearch1Parameter(String name, String key, String value,
			int size) {
		log.info(name + " start!");
		try {
			login();
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(sessionId));

			String str = String.format("{\"token\":\"%s\", \"" + key
					+ "\":\"%s\"}", sessionId, value);
			JsonNode json = callAPI("users/search", str);
			log.info(json);
			JsonNode details = new JsonNode(super.getStringFromJson(json,
					"details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be " + size, size, details
					.getArray().length());
			for (int i = 0; i < details.getArray().length(); i++) {
				String public_info = super.getStringFromJson(new JsonNode(
						String.valueOf(details.getArray().get(i))),
						"public_info");
				String firstName = super.getStringFromJson(new JsonNode(
						public_info), "first_name");
				String lastName = super.getStringFromJson(new JsonNode(
						public_info), "last_name");
				String email = super.getStringFromJson(
						new JsonNode(public_info), "email");
				String mobile = super.getStringFromJson(new JsonNode(
						public_info), "mobile");
				boolean isMatch = (value.equals(firstName)
						|| value.equals(lastName) || value.equals(email) || value
						.equals(mobile));
				Assert.assertTrue(public_info+" should contain "+value,isMatch);
			}
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	private void testSearch2Parameters(String name, String[] key,
			String[] value, int size) {
		log.info(name + " start!");
		try {
			login();
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(sessionId));

			String str = String.format("{\"token\":\"%s\", \"" + key[0]
					+ "\":\"%s\", \"" + key[1] + "\":\"%s\"}", sessionId,
					value[0], value[1]);
			JsonNode json = callAPI("users/search", str);
			log.info(json);
			JsonNode details = new JsonNode(super.getStringFromJson(json,
					"details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be " + size, size, details
					.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	private void testSearch3Parameters(String name, String[] key,
			String[] value, int size) {
		log.info(name + " start!");
		try {
			login();
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(sessionId));

			String str = String.format("{\"token\":\"%s\", \"" + key[0]
					+ "\":\"%s\", \"" + key[1] + "\":\"%s\", \"" + key[2]
					+ "\":\"%s\"}", sessionId, value[0], value[1], value[2]);
			JsonNode json = callAPI("users/search", str);
			log.info(json);
			JsonNode details = new JsonNode(super.getStringFromJson(json,
					"details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be " + size, size, details
					.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	private void testSearch4Parameters(String name, String[] key,
			String[] value, int size) {
		log.info(name + " start!");
		try {
			login();
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(sessionId));

			String str = String.format("{\"token\":\"%s\", \"" + key[0]
					+ "\":\"%s\", \"" + key[1] + "\":\"%s\", \"" + key[2]
					+ "\":\"%s\", \"" + key[3] + "\":\"%s\"}", sessionId,
					value[0], value[1], value[2], value[3]);
			JsonNode json = callAPI("users/search", str);
			log.info(json);
			JsonNode details = new JsonNode(super.getStringFromJson(json,
					"details"));
			log.info(details.getArray().length());
			Assert.assertEquals("size should be " + size, size, details
					.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}

	@Test
	public void testSearchByFirstName() {
		testSearch1Parameter("testSearchByFirstName", "keyword", "Sports_1", 2);
	}

	@Test
	public void testSearchByLastName() {
		testSearch1Parameter("testSearchByLastName", "keyword", "Puluo_1", 3);
	}

	@Test
	public void testSearchByEmail() {
		testSearch1Parameter("testSearchByEmail", "keyword",
				"sinorockie@puluo_1.com", 2);
	}

	@Test
	public void testSearchByMobile() {
		testSearch1Parameter("testSearchByMobile", "keyword", "123456789", 1);
	}

	/*
	 * @Test public void testSearchBy2Parameters() {
	 * testSearch2Parameters("testSearchBy2Parameters: First Name/Last Name",
	 * new String[]{"first_name", "last_name"}, new String[]{"Sports_1",
	 * "Puluo_1"}, 2);
	 * testSearch2Parameters("testSearchBy2Parameters: First Name/Mobile", new
	 * String[]{"first_name", "mobile"}, new String[]{"Sports_1", "123456789"},
	 * 1); testSearch2Parameters("testSearchBy2Parameters: First Name/Email",
	 * new String[]{"first_name", "email"}, new String[]{"Sports_1",
	 * "sinorockie@puluo_2.com"}, 1);
	 * testSearch2Parameters("testSearchBy2Parameters: Last Name/Mobile", new
	 * String[]{"mobile", "last_name"}, new String[]{"23456789", "Puluo_2"}, 0);
	 * testSearch2Parameters("testSearchBy2Parameters: Last Name/Email", new
	 * String[]{"email", "last_name"}, new String[]{"sinorockie@puluo_1.com",
	 * "Puluo_2"}, 1);
	 * testSearch2Parameters("testSearchBy2Parameters: Email/Mobile", new
	 * String[]{"email", "mobile"}, new String[]{"sinorockie@puluo_1.com",
	 * "123456789"}, 1); }
	 * 
	 * @Test public void testSearchBy3Parameters() {
	 * testSearch3Parameters("testSearchBy3Parameters: First Name/Last Name/Mobile"
	 * , new String[]{"first_name", "last_name", "mobile"}, new
	 * String[]{"Sports_1", "Puluo_1", "123456789"}, 1);
	 * testSearch3Parameters("testSearchBy3Parameters: First Name/Mobile/Email",
	 * new String[]{"first_name", "mobile", "email"}, new String[]{"Sports_1",
	 * "123456789", "sinorockie@puluo_1.com"}, 1);
	 * testSearch3Parameters("testSearchBy3Parameters: First Name/Last Name/Email"
	 * , new String[]{"first_name", "last_name", "email"}, new
	 * String[]{"Sports_1", "Puluo_1", "sinorockie@puluo_2.com"}, 1);
	 * testSearch3Parameters("testSearchBy3Parameters: Last Name/Mobile/Email",
	 * new String[]{"last_name", "mobile", "email"}, new String[]{"Puluo_2",
	 * "23456789", "sinorockie@puluo_3.com"}, 0); }
	 * 
	 * @Test public void testSearchBy4Parameters() { testSearch4Parameters(
	 * "testSearchBy4Parameters: First Name/Last Name/Email/Mobile", new
	 * String[]{"first_name", "last_name", "email", "mobile"}, new
	 * String[]{"Sports_3", "Puluo_2", "sinorockie@puluo_1.com", "456789012"},
	 * 1); testSearch4Parameters(
	 * "testSearchBy4Parameters: First Name/Last Name/Email/Mobile", new
	 * String[]{"first_name", "last_name", "email", "mobile"}, new
	 * String[]{"Sports_3", "Puluo_2", "sinorockie@puluo_1.com", "123456789"},
	 * 0); }
	 */
}
