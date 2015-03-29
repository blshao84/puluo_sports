package com.puluo.test.functional;

import org.junit.Assert;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class LoginFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(LoginFunctionalTest.class);
	@Test
	public void testCorrectPassword() {
		String inputs = "{\"password\":\"uvwxyz\",\"mobile\":\"18521564305\"}";
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			String session = getSessionID(json2);
			log.info("successfully aquired session:"+session);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
