package com.puluo.test.result;

import static org.junit.Assert.*;
import org.junit.Test;

import com.puluo.api.result.UserProfileResult;

public class UserProfileResultTest {

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

}
