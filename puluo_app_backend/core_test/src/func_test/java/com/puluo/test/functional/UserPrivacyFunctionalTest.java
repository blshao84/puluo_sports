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
import com.puluo.dao.impl.PuluoUserSettingDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class UserPrivacyFunctionalTest extends APIFunctionalTest {
	
	public static Log log = LogFactory.getLog(UserPrivacyFunctionalTest.class);
	
	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String sessionId;

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoUserSettingDaoImpl settingDao = (PuluoUserSettingDaoImpl) DaoApi.getInstance().userSettingDao();
		dao.save(mobile1, password1);
		uuid1 = dao.getByMobile(mobile1).userUUID();
		settingDao.saveNewSetting(uuid1);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoUserSettingDaoImpl settingDao = (PuluoUserSettingDaoImpl) DaoApi.getInstance().userSettingDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			settingDao.deleteByUserUUID(user.userUUID());
		}
	}

	@Test
	public void testLoginUser() {
		log.info("testLoginUser start!");
		try {
			login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(sessionId));
			
			String str = String.format("{\"token\":\"%s\"}", sessionId);
			JsonNode json = callAPI("users/privacy/", str);
			log.info(json);
			String uuid = super.getStringFromJson(json, "user_uuid");
			Assert.assertEquals("uuid should be " + uuid1, uuid1, uuid);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testLoginUser done!");
	}

	private void login() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}", password1, mobile1);
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			sessionId = getSessionID(json2);
			log.info("successfully aquired session:" + sessionId);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
