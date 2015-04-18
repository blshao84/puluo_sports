package com.puluo.test.functional;

import org.junit.Ignore;
import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PasswordEncryptionFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(LogoutFunctionalTest.class);

	public static String mobile = "18521564305";
	public static String passwordText = "qqqqqq";
	public static String host = "http://localhost:3333/encode";

	@Test @Ignore
	public void testPasswordEncryption() {
		try {
			HttpResponse<String> request = Unirest.get(host)
					.queryString("key", passwordText).asString();
			System.out.println(request.getBody());
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
