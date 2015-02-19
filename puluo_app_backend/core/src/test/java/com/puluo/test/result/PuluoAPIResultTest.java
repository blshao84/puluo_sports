package com.puluo.test.result;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.puluo.api.result.ApproveFriendResult;
import com.puluo.api.result.DeleteFriendResult;
import com.puluo.api.result.DenyFriendResult;
import com.puluo.api.result.ListFriendsResult;
import com.puluo.api.result.ListMessageResult;
import com.puluo.api.result.RequestFriendResult;
import com.puluo.api.result.SendMessageResult;
import com.puluo.api.result.UserLoginResult;
import com.puluo.api.result.UserLogoutResult;
import com.puluo.api.result.UserPasswordUpdateResult;
import com.puluo.api.result.UserProfileResult;
import com.puluo.api.result.UserProfileUpdateResult;
import com.puluo.api.result.UserRegistrationResult;
import com.puluo.api.result.UserSearchResult;
import com.puluo.api.result.UserSettingResult;
import com.puluo.api.result.UserSettingUpdateResult;

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

	@Test
	public void testDummyUserProfileUpdateResult(){
		String expectedJsonResult = "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{"
				+ "\"first_name\":\"Tracey\","
				+ "\"last_name\":\"Boyd\","
				+ "\"thumbnail\":\"http://upyun/puluo/userThumb.jpg!200\","
				+ "\"large_image\":\"http://upyun/puluo/userThumb.jpg\","
				+ "\"saying\":\"I’ve got an app for that.\""
				+ "},"
				+ "\"private_info\":{"
				+ "\"email\":\"tracey.boyd@kotebo.com\","
				+ "\"sex\":\"m\","
				+ "\"birthday\":\"1984-09-12\","
				+ "\"occupation\":\"Internet Plumber\","
				+ "\"country\":\"USA\",\"state\":\"Washington\","
				+ "\"city\":\"Seattle\",\"zip\":\"234234\"},"
				+ "\"created_at\":\"2012-01-01 12:00:00\","
				+ "\"updated_at\":\"2012-01-01 12:00:00\"}";
		String actualJsonResult = UserProfileUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult,expectedJsonResult);
	}

	@Test
	public void testDummyUserSearchResult(){
		String expectedJsonResult = "{"
				+"\"details\":["
				+"{"
				+"\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+"\"public_info\":{"
					+"\"first_name\":\"baolins\","
					+"\"last_name\":\"Boyd\","
					+"\"email\":\"tracey.boyd@kotebo.com\","
					+"\"mobile\":\"123456789000\""
					+"}"
				+"},"
				+"{"
				+"\"uuid\":\"ze2345d54-75b4-3234-adb2-ajfs230948jsdf\","
				+"\"public_info\":{"
					+"\"first_name\":\"baolins\","
					+"\"last_name\":\"Shao\","
					+"\"email\":\"blshao@qq.com\","
					+"\"mobile\":\"18646655333\""
					+"}"
				+"}"
				+"]"
		  +"}";
		String actualJsonResult = UserSearchResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testUserSettingResult(){
		String expectedJsonResult = "{"
			+"\"user_uuid\":\"\","
			+"\"auto_add_friend\":true,"
			+"\"allow_stranger_view_timeline\":true,"
			+"\"allow_searched\":true"
			+"}";
		String actualJsonResult = UserSettingResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testUserSettingUpdateResult(){
		String expectedJsonResult = "{"
			+"\"user_uuid\":\"\","
			+"\"auto_add_friend\":true,"
			+"\"allow_stranger_view_timeline\":true,"
			+"\"allow_searched\":true"
			+"}";
		String actualJsonResult = UserSettingUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testListFriendsResult(){
		String expectedJsonResult = "{"
			  +"\"details\":["
				  +"{"
				  +"\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				  +"\"public_info\":{"
					  +"\"first_name\":\"baolins\","
					  +"\"last_name\":\"Boyd\","
					  +"\"email\":\"tracey.boyd@kotebo.com\","
					  +"\"mobile\":\"123456789000\""
					  +"}"
				  +"},"
				  +"{"
				  +"\"uuid\":\"ze2345d54-75b4-3234-adb2-ajfs230948jsdf\","
					  +"\"public_info\":{"
					  +"\"first_name\":\"baolins\","
					  +"\"last_name\":\"Shao\","
					  +"\"email\":\"blshao@qq.com\","
					  +"\"mobile\":\"18646655333\""
					  +"}"
				  +"}"
				  +"]"
			  +"}";
		String actualJsonResult = ListFriendsResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testRequestFriendResult(){
		String expectedJsonResult = "{"
				+"\"friend_request\":{"
					+"\"request_id\":\"\","
					+"\"status\":\"pending\","
					+"\"messages\":["
						+"{"
						+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"content\":\"hi, this is Tracy!\","
						+"\"approval\":\"pending\","
						+"\"created_at\":\"2012-01-01 12:00:00\""
						+"}"
					+"],"
					+"\"created_at\":\"2012-01-01 12:00:00\","
					+"\"updated_at\":\"2012-01-01 12:00:00\""
					+"}"
				+"}";
		String actualJsonResult = RequestFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDeleteFriendResult(){
		String expectedJsonResult = "{"
				+"\"past_messages\":["
					+"{"
					+"\"msg_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\""
					+"},"
					+"{"
					+"\"msg_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\""
					+"}"
				+"]"
			+"}";
		String actualJsonResult = DeleteFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDenyFriendResult(){
		String expectedJsonResult = "{"
				+"\"friend_request\":{"
					+"\"request_id\":\"\","
					+"\"status\":\"denied\","
					+"\"messages\":["
						+"{"
						+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"content\":\"hi, this is Tracy!\","
						+"\"created_at\":\"2012-01-01 12:00:00\""
						+"},"
						+"{"
						+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"content\":\"hi, this is Tracy!\","
						+"\"created_at\":\"2012-01-01 12:00:00\""
						+"}"
					+"],"
					+"\"created_at\":\"2012-01-01 12:00:00\","
					+"\"updated_at\":\"2012-01-01 12:00:00\""
					+"}"
				+"}";
		String actualJsonResult = DenyFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testApproveFriendResult(){
		String expectedJsonResult = "{"
				+"\"friend_request\":{"
					+"\"request_id\":\"\","
					+"\"status\":\"approved\","
					+"\"messages\":["
						+"{"
						+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"content\":\"hi, this is Tracy!\","
						+"\"created_at\":\"2012-01-01 12:00:00\""
						+"},"
						+"{"
						+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
						+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
						+"\"content\":\"hi, this is Tracy!\","
						+"\"created_at\":\"2012-01-01 12:00:00\""
						+"}"
					+"],"
					+"\"created_at\":\"2012-01-01 12:00:00\","
					+"\"updated_at\":\"2012-01-01 12:00:00\""
					+"}"
				+"}";
		String actualJsonResult = ApproveFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testSendMessageResult(){
		String expectedJsonResult = "{"
					+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
					+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
					+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
					+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
					+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
					+"\"content\":\"hi, this is Tracy!\","
					+"\"created_at\":\"2012-01-01 12:00:00\""
				+"}";
		String actualJsonResult = SendMessageResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testListMessageResult(){
		String expectedJsonResult = "{"
					+ "\"messages\":["
						+ "{"
							+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
							+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
							+"\"content\":\"hi, this is Tracy!\","
							+"\"created_at\":\"2012-01-01 12:00:00\""
						+ "},"
						+ "{"
							+"\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
							+"\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
							+"\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
							+"\"content\":\"hi, this is Tracy!\","
							+"\"created_at\":\"2012-01-01 12:00:00\""
						+ "}"
					+ "]"
				+"}";
		String actualJsonResult = ListMessageResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}
}
