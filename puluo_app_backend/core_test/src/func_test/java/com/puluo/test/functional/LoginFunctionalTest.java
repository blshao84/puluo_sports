package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class LoginFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(LoginFunctionalTest.class);
	static String mobile1 = "123456789";
	static String password1 = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, password1);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl)DaoApi.getInstance().userDao();
		PuluoUser user = dao.getByMobile(mobile1);
		dao.deleteByUserUUID(user.userUUID());
	}

	@Test
	public void testCorrectPassword() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}",password1,mobile1);
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			String session = getSessionID(json2);
			log.info("successfully aquired session:" + session);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testIncorrectPassword() {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}","xzysjdfsd",mobile1);
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			System.out.println(json2);
			/*String error = super.getStringFromJson(json2, "id");
			log.info("fail to login, error code =" + error);
			Assert.assertEquals(
					"unsuccessful login due to incorrect password should give error id ","1",error);*/
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}


}
