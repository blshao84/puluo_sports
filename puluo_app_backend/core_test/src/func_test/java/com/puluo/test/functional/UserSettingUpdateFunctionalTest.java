package com.puluo.test.functional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class UserSettingUpdateFunctionalTest extends APIFunctionalTest {
	
	public static Log log = LogFactory.getLog(UserSettingUpdateFunctionalTest.class);
	
	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String sessionId;
	static Map<String, Integer> i = new HashMap<String, Integer>();
	
	{
		i.put("auto_add_friend", 0);
		i.put("allow_stranger_view_timeline", 1);
		i.put("allow_searched", 2);
	}

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		userDao.save(mobile1, password1);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		PuluoUserSettingDaoImpl settingDao = (PuluoUserSettingDaoImpl) DaoApi.getInstance().userSettingDao();
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

	private void test(String name, String key, String value) {
		log.info(name + " start!");
		try {
			login();
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(sessionId));
			
			String str = String.format("{\"token\":\"%s\", \"" + key + "\":\"%s\"}", sessionId, value);
			JsonNode json = callAPI("users/setting/update", str);
			log.info(json);
			List<JsonNode> result = super.getJsonArrayFromJson(json, "settings");
			Assert.assertEquals(key.replace("_", " ") + " should be " + value, value, super.getStringFromJson(result.get(i.get(key)), "value"));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info(name + " done!");
	}
	
	@Test
	public void testAutoAddFriend() {
		test("testAutoAddFriend", "auto_add_friend", "false");
	}
	
	@Test
	public void testAllowStrangerViewTimeline() {
		test("testAllowStrangerViewTimeline", "allow_stranger_view_timeline", "false");
	}
	
	@Test
	public void testAllowSearched() {
		test("testAllowSearched", "allow_searched", "false");
	}
}
