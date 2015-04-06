package com.puluo.test.functional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserSetting;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class RegisterFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(RegisterFunctionalTest.class);
	static String existingMobile = "123456789";
	static String existingPassword = "abcdefg";
	static String existingAuthCode = "123456";
	static String nonExistingMobile1 = "111222333444";
	static String nonExistingPassword1 = "abcdefg";
	static String nonExistingAuthCode = "123456";

	@Before
	public void setupDB() {
		cleanupDB();
		PuluoDSI dsi = DaoApi.getInstance();
		dsi.userDao().save(existingMobile, existingPassword);
		dsi.authCodeRecordDao().upsertRegistrationAuthCode(existingMobile,existingAuthCode);
		dsi.authCodeRecordDao().upsertRegistrationAuthCode(nonExistingMobile1, nonExistingAuthCode);
	}

	@After
	public void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl) DaoApi
				.getInstance().authCodeRecordDao();
		authDao.deleteByMobile(existingMobile);
		authDao.deleteByMobile(nonExistingMobile1);
		PuluoUser user1 = dao.getByMobile(existingMobile);
		PuluoUser user2 = dao.getByMobile(nonExistingMobile1);
		if (user1 != null) {
			dao.deleteByUserUUID(user1.userUUID());
		}
		if (user2 != null) {
			dao.deleteByUserUUID(user2.userUUID());
		}
	}

	@Test
	public void testSuccessfulRegisteration() {
		String inputs = String.format("{\"password\":\"%s\","
				+ "\"mobile\":\"%s\"," + "\"auth_code\":\"%s\"" + "}",
				nonExistingPassword1, nonExistingMobile1,nonExistingAuthCode);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String uuid = super.getStringFromJson(json2, "user_uuid");
			String password = super.getStringFromJson(json2, "password");
			String mobile = super.getStringFromJson(json2, "mobile");
			PuluoUser user = DaoApi.getInstance().userDao().getByMobile(nonExistingMobile1);
			Assert.assertNotNull("user should be successfully registered",user);
			Assert.assertEquals("password in db should match", nonExistingPassword1,user.password());
			Assert.assertEquals("password in returned json should match", nonExistingPassword1,password);
			Assert.assertEquals("mobile in returned json should match",nonExistingMobile1,mobile);
			Assert.assertNotEquals("uuid in returned json shouldn't be empty","",uuid);
			PuluoUserSetting setting = DaoApi.getInstance().userSettingDao().getByUserUUID(uuid);
			Assert.assertNotNull("a new setting should be saved together with a new user",setting);
			Assert.assertTrue("by default autoAddFriend is true",setting.autoAddFriend());
			Assert.assertTrue("by default isSearchable is true",setting.isSearchable());
			Assert.assertTrue("by default isTimelinePublic is true",setting.isTimelinePublic());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testWrongAuthCode() {
		String inputs = String.format("{\"password\":\"%s\","
				+ "\"mobile\":\"%s\"," + "\"auth_code\":\"654321\"" + "}",
				nonExistingPassword1, nonExistingMobile1);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String id = super.getStringFromJson(json2, "id");
			Assert.assertEquals("error id should be 7","7",id);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testMissingAuthCode() {
		String inputs = String.format("{\"password\":\"%s\","
				+ "\"mobile\":\"%s\"" + "}",
				nonExistingPassword1, nonExistingMobile1);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String id = super.getStringFromJson(json2, "id");
			Assert.assertEquals("error id should be 15","15",id);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testMissingPassword() {
		String inputs = String.format("{"
				+ "\"mobile\":\"%s\"," + "\"auth_code\":\"%s\"" + "}",
				nonExistingMobile1,nonExistingAuthCode);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String error = super.getStringFromJson(json2, "id");
			Assert.assertEquals("error id should be 15","15", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testMissingMobile() {
		String inputs = String.format("{"
				+ "\"password\":\"%s\"," + "\"auth_code\":\"%s\"" + "}",
				nonExistingPassword1,nonExistingAuthCode);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String error = super.getStringFromJson(json2, "id");
			Assert.assertEquals("error id should be 15","15", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testExistingMobile(){
		String inputs = String.format("{\"password\":\"%s\","
				+ "\"mobile\":\"%s\"," + "\"auth_code\":\"%s\"" + "}",
				existingPassword, existingMobile,existingAuthCode);
		try {
			JsonNode json2 = callAPI("users/register", inputs);
			String id = super.getStringFromJson(json2, "id");
			Assert.assertEquals("error id should be 5","5",id);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
