package com.puluo.test.functional;


import java.util.ArrayList;

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
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class EventConfigurationFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(EventConfigurationFunctionalTest.class);
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
	public void testConfiguration(){
		super.runAuthenticatedTest(new PuluoAuthenticatedFunctionalTestRunner() {
			
			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("events/configurations", inputs(session));
				String expectedCategories = "[\"Stamina\",\"Yoga\",\"Dance\",\"Health\",\"Others\"]";
				String expectedLevels = "[\"Level1\",\"Level2\",\"Level3\",\"Level4\"]";
				String actualCategories = getStringFromJson(json, "categories");
				String actualLevels = getStringFromJson(json, "levels");
				ArrayList<JsonNode> actualGeos = getJsonArrayFromJson(json, "geo");
				Assert.assertEquals("should have 36 states",36, actualGeos.size());
				Assert.assertEquals("",expectedCategories, actualCategories);
				Assert.assertEquals("",expectedLevels, actualLevels);
				
			}
			
			@Override
			public String password() {
				return password1;
			}
			
			@Override
			public String mobile() {
				return mobile1;
			}
			
			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\"}", session);
			}
		});
	}
}
