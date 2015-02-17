package com.puluo.test.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.puluo.api.result.UserLoginResult;
import com.puluo.api.result.UserLogoutResult;
import com.puluo.api.result.UserPasswordUpdateResult;
import com.puluo.api.result.UserProfileResult;
import com.puluo.api.result.UserRegistrationResult;

public class PuluoAPIResultTest {

	@Test
	public void testDummyUserProfileResult() {
		String expectedJsonResult = 
				"{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{"
				+ "\"first_name\":\"Tracy\","
				+ "\"last_name\":\"Boyd\","
				+ "\"thumbnail\":\"http://upyun/puluo/userThumb.jpg!200\","
				+ "\"large_image\":\"http://upyun/puluo/userThumb.jpg\","
				+ "\"saying\":\"I’ve got an app for that.\","
				+ "\"likes\":2,"
				+ "\"banned\":false,"
				+ "\"following\":1,"
				+ "\"is_coach\":false"
				+ "},"
				+ "\"private_info\":{"
				+ "\"email\":\"tracey.boyd@kotebo.com\","
				+ "\"sex\":\"m\","
				+ "\"birthday\":\"1984-09-12\","
				+ "\"occupation\":\"Internet Plumber\","
				+ "\"country\":\"USA\","
				+ "\"state\":\"Washington\","
				+ "\"city\":\"Seattle\","
				+ "\"zip\":\"234234\""
				+ "},"
				+ "\"created_at\":\"2012-01-01 12:00:00\","
				+ "\"updated_at\":\"2012-01-01 12:00:00\""
				+ "}";
		String actualJsonResult = UserProfileResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}
	
	@Test
	public void testDummyUserRegistrationResult(){
		String expectedJsonResult = 
				"{"
				+ "\"mobile\":\"12346789000\","
				+ "\"password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\""
				+ "}";
		String actualJsonResult = UserRegistrationResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}
	
	@Test
	public void testDummyUserLoginResult(){
		String expectedJsonResult = 
				"{"
				+ "\"mobile\":\"12346789000\","
				+ "\"password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\""
				+ "}";
		String actualJsonResult = UserLoginResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}
	
	@Test
	public void testDummyUserLogoutResult(){
		String expectedJsonResult = 
				"{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"duration_seconds\":12345"
				+ "}";
		String actualJsonResult = UserLogoutResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}
	
	@Test
	public void testDummyUserPasswordUpdateResult(){
		String expectedJsonResult = 
				"{"
				+ "\"password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\","
				+ "\"new_password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\""
				+ "}";
		String actualJsonResult = UserPasswordUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}

}
