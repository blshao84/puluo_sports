package com.puluo.test.result;


import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.puluo.result.EmailServiceResult;
import com.puluo.result.PuluoConfigurationResult;
import com.puluo.result.SMSServiceResult;
import com.puluo.result.UserTimelineResult;
import com.puluo.result.event.EventDetailResult;
import com.puluo.result.message.ApproveFriendResult;
import com.puluo.result.message.DeleteFriendResult;
import com.puluo.result.message.ListMessageResult;
import com.puluo.result.message.RequestFriendResult;
import com.puluo.result.message.SendMessageResult;
import com.puluo.result.user.CommonListAPIResult;
import com.puluo.result.user.DenyFriendResult;
import com.puluo.result.user.UserLoginResult;
import com.puluo.result.user.UserLogoutResult;
import com.puluo.result.user.UserPasswordUpdateResult;
import com.puluo.result.user.UserProfileResult;
import com.puluo.result.user.UserProfileUpdateResult;
import com.puluo.result.user.UserRegistrationResult;
import com.puluo.result.user.UserSearchResult;
import com.puluo.result.user.UserSettingResult;
import com.puluo.result.user.UserSettingUpdateResult;

public class PuluoAPIResultTest {

	@Test
	public void testDummyEventConfigurationResult() {
		String expected = "{\"categories\":[\"Stamina\",\"Yoga\",\"Dance\",\"Health\",\"Others\"],\"levels\":[\"Level1\",\"Level2\",\"Level3\",\"Level4\"]}";
		String actual = PuluoConfigurationResult.dummy().toJson();
		Assert.assertEquals(
						"dummy event configuration result should be the same as PuluoEventConfiguraiotn and PuluoEventLevel",
						expected, actual);
	}

	@Test
	public void testDummyUserProfileResult() {
		String expectedJsonResult = "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{" + "\"first_name\":\"Tracy\","
				+ "\"last_name\":\"Boyd\","
				+ "\"thumbnail\":\"http://upyun/puluo/userThumb.jpg!200\","
				+ "\"large_image\":\"http://upyun/puluo/userThumb.jpg\","
				+ "\"saying\":\"I’ve got an app for that.\"," + "\"likes\":2,"
				+ "\"banned\":false," + "\"following\":\"true\","
				+ "\"is_coach\":false," 
				+ "\"last_login\":10000000"+ "},"
				+ "\"private_info\":{"
				+ "\"email\":\"tracey.boyd@kotebo.com\"," + "\"sex\":\"m\","
				+ "\"birthday\":\"1984-09-12\","
				+ "\"occupation\":\"Internet Plumber\","
				+ "\"country\":\"USA\"," + "\"state\":\"Washington\","
				+ "\"city\":\"Seattle\"," + "\"zip\":\"234234\","
				+ "\"pending\":[{\"friend_request\":{"
				+ "\"request_id\":\"\",\"status\":\"pending\","
				+ "\"messages\":[{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\",\"created_at\":1427007059034}],"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034}}]"
				+ "},"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034" + "}";
		String actualJsonResult = UserProfileResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserRegistrationResult() {
		String expectedJsonResult = "{"
				+ "\"user_uuid\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\","
				+ "\"mobile\":\"12346789000\","
				+ "\"password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\""
				+ "}";
		String actualJsonResult = UserRegistrationResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserLoginResult() {
		String expectedJsonResult = "{"
				+ "\"uuid\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\","
				+ "\"created_at\":1427007059034,"
				+ "\"last_login\":1427007059034" + "}";
		String actualJsonResult = UserLoginResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserLogoutResult() {
		String expectedJsonResult = "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"duration_seconds\":12345" + "}";
		String actualJsonResult = UserLogoutResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserPasswordUpdateResult() {
		String expectedJsonResult = "{"
				+ "\"auth_code\":\"123456\","
				+ "\"new_password\":\"cd8460a5e0f2c2af596f170009bffc02df06b54d\""
				+ "}";
		String actualJsonResult = UserPasswordUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserProfileUpdateResult() {
		String expectedJsonResult = "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{" + "\"first_name\":\"Tracey\","
				+ "\"last_name\":\"Boyd\","
				+ "\"thumbnail\":\"http://upyun/puluo/userThumb.jpg!200\","
				+ "\"large_image\":\"http://upyun/puluo/userThumb.jpg\","
				+ "\"saying\":\"I’ve got an app for that.\"" + "},"
				+ "\"private_info\":{"
				+ "\"email\":\"tracey.boyd@kotebo.com\"," + "\"sex\":\"m\","
				+ "\"birthday\":\"1984-09-12\","
				+ "\"occupation\":\"Internet Plumber\","
				+ "\"country\":\"USA\",\"state\":\"Washington\","
				+ "\"city\":\"Seattle\",\"zip\":\"234234\"},"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034}";
		String actualJsonResult = UserProfileUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDummyUserSearchResult() {
		String expectedJsonResult = "{" + "\"details\":[" + "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{" + "\"first_name\":\"baolins\","
				+ "\"last_name\":\"Boyd\","
				+ "\"email\":\"tracey.boyd@kotebo.com\","
				+ "\"mobile\":\"123456789000\","
				+ "\"thumbnail\":\"http://www.puluosports.com/logo.jpg\","
				+ "\"saying\":\"I'm not Baolin!\"" + "}" + "}," + "{"
				+ "\"uuid\":\"ze2345d54-75b4-3234-adb2-ajfs230948jsdf\","
				+ "\"public_info\":{" + "\"first_name\":\"baolins\","
				+ "\"last_name\":\"Shao\"," + "\"email\":\"blshao@qq.com\","
				+ "\"mobile\":\"18646655333\","
				+ "\"thumbnail\":\"http://www.puluosports.com/logo.jpg\","
				+ "\"saying\":\"I'm Baolin.\"" + "}" + "}" + "]" + "}";
		String actualJsonResult = UserSearchResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testUserSettingResult() {
		String expectedJsonResult = 
		"{\"user_uuid\":\"\","
		+ "\"settings\":["
		+ "{\"key\":\"auto_add_friend\",\"name\":\"允许自动添加好友\",\"value\":true},"
		+ "{\"key\":\"allow_stranger_view_timeline\",\"name\":\"允许陌生人浏览\",\"value\":true},"
		+ "{\"key\":\"allow_searched\",\"name\":\"允许被搜索到\",\"value\":true}"
		+ "]}";
		String actualJsonResult = UserSettingResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testUserSettingUpdateResult() {
		String expectedJsonResult = 
				"{\"user_uuid\":\"\","
				+ "\"settings\":["
				+ "{\"key\":\"auto_add_friend\",\"name\":\"允许自动添加好友\",\"value\":true},"
				+ "{\"key\":\"allow_stranger_view_timeline\",\"name\":\"允许陌生人浏览\",\"value\":true},"
				+ "{\"key\":\"allow_searched\",\"name\":\"允许被搜索到\",\"value\":true}"
				+ "]}";
		String actualJsonResult = UserSettingUpdateResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testListFriendsResult() {
		String expectedJsonResult = "{" + "\"details\":[" + "{"
				+ "\"uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"public_info\":{" + "\"first_name\":\"baolins\","
				+ "\"last_name\":\"Boyd\","
				+ "\"email\":\"tracey.boyd@kotebo.com\","
				+ "\"mobile\":\"123456789000\","
				+ "\"thumbnail\":\"http://upyun.com/tracey.jpg\","
				+ "\"saying\":\"I am Tracey\"" + "}" + "}," + "{"
				+ "\"uuid\":\"ze2345d54-75b4-3234-adb2-ajfs230948jsdf\","
				+ "\"public_info\":{" + "\"first_name\":\"baolins\","
				+ "\"last_name\":\"Shao\"," + "\"email\":\"blshao@qq.com\","
				+ "\"mobile\":\"18646655333\","
				+ "\"thumbnail\":\"http://upyun.com/baolin.jpg\","
				+ "\"saying\":\"I am Baolin\"" + "}" + "}" + "]" + "}";
		String actualJsonResult = CommonListAPIResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testRequestFriendResult() {
		String expectedJsonResult = "{" + "\"friend_request\":{"
				+ "\"request_id\":\"\"," + "\"status\":\"pending\","
				+ "\"messages\":[" + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}" + "],"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034" + "}" + "}";
		String actualJsonResult = RequestFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDeleteFriendResult() {
		String expectedJsonResult = "{\"friends\":[\"de305d54-75b4-431b-adb2-eb6b9e546012\",\"de305d54-75b4-431b-adb2-eb6b9e546014\"]}";
		String actualJsonResult = DeleteFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testDenyFriendResult() {
		String expectedJsonResult = "{" + "\"friend_request\":{"
				+ "\"request_id\":\"\"," + "\"status\":\"denied\","
				+ "\"messages\":[" + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}," + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}" + "],"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034" + "}" + "}";
		String actualJsonResult = DenyFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testApproveFriendResult() {
		String expectedJsonResult = "{" + "\"friend_request\":{"
				+ "\"request_id\":\"\"," + "\"status\":\"approved\","
				+ "\"messages\":[" + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}," + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}" + "],"
				+ "\"created_at\":1427007059034,"
				+ "\"updated_at\":1427007059034" + "}" + "}";
		String actualJsonResult = ApproveFriendResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testSendMessageResult() {
		String expectedJsonResult = "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}";
		String actualJsonResult = SendMessageResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testListMessageResult() {
		String expectedJsonResult = "{" + "\"messages\":[" + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}," + "{"
				+ "\"msg_id\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"to_user\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"from_user_first_name\":\"Baolin\","
				+ "\"to_user_first_name\":\"Shao\","
				+ "\"from_user_last_name\":\"Tracey\","
				+ "\"to_user_last_name\":\"Boydston\","
				+ "\"from_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"to_user_thumbnail\":\"http://upyun.com/puluo/xxxx\","
				+ "\"content\":\"hi, this is Tracy!\","
				+ "\"created_at\":1427007059034" + "}" + "],\"total_count\":2" + "}";
		String actualJsonResult = ListMessageResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testEventDetailResult() {
		String expectedJsonResult = "{"
				+ "\"status\":\"open\","
				+ "\"event_name\":\"Weapons of Ass Reduction\","
				+ "\"event_time\":1427007059034,"
				+ "\"address\":\"888 Happy Mansion\","
				+ "\"city\":\"beijing\","
				+ "\"phone\":\"86-555-5555\","
				+ "\"coach_name\":\"Mr. Bob Smith\","
				+ "\"coach_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546014\","
				+ "\"posters\":[\"http://upyun.com/puluo/head.jpg\"],"
				+ "\"registered_users\":22,"
				+ "\"capacity\":30,"
				+ "\"likes\":2,"
				+ "\"geo_location\":{"
				+ "\"latitude\":39.92889,"
				+ "\"longitude\":116.38833"
				+ "},"
				+ "\"details\":\"Get fit with friends.\","
				+ "\"memories\":[\"http://upyun.com/puluo/image1.jpg\","
				+ "\"http://upyun.com/puluo/image2.jpg\""
				+ "],"
				+ "\"price\":0.0,"
				+ "\"attendees\":[{\"name\":\"Lei\",\"uuid\":\"0\",\"thumbnail\":\"http://upyun.com/puluo/thumbnail0.jpg\"},"
				+ "{\"name\":\"Baolin\",\"uuid\":\"1\",\"thumbnail\":\"http://upyun.com/puluo/thumbnail1.jpg\"}],"
				+ "\"registered\":false,"
				+ "\"duration\":30}";
		
		String actualJsonResult = EventDetailResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testUserTimelineResult() {
		String expectedJsonResult = "{" + "\"timelines\":[" + "{"
				+ "\"timeline_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"event\":{"
				+ "\"event_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"event_name\":\"Weapon of big ass reduction\","
				+ "\"created_at\":1427007059034" + "},"
				+ "\"my_words\":\"This is an awesome event\"," + "\"likes\":["
				+ "{\"user_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"user_name\":\"Bob\"," + "\"created_at\":1427007059034"
				+ "},"
				+ "{\"user_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"user_name\":\"Bob\"," + "\"created_at\":1427007059034"
				+ "}]," + "\"comments\":[" + "{"
				+ "\"comment_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"reply_to_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"user_uuid\":\"de305d54-75b4-431b-adb2-eb6b9e546013\","
				+ "\"user_name\":\"Bob\"," + "\"content\":\"abc\","
				+ "\"read\":\"false\"," + "\"created_at\":1427007059034"
				+ "}]," + "\"create_at\":1427007059034,"
				+ "\"updated_at\":1427007059034}]}";

		String actualJsonResult = UserTimelineResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testEmailServiceResult() {
		String expectedJsonResult = "{" + "\"email\":\"bshao@163.com\","
				+ "\"status\":\"success\"" + "}";
		String actualJsonResult = EmailServiceResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	@Test
	public void testSMSServiceResult() {
		String expectedJsonResult = "{" + "\"mobile\":\"1234567890\","
				+ "\"status\":\"success\"" + "}";
		String actualJsonResult = SMSServiceResult.dummy().toJson();
		assertEquals(actualJsonResult, expectedJsonResult);
	}

	public static void main(String[] args) {
		PuluoAPIResultTest test = new PuluoAPIResultTest();
		test.testApproveFriendResult();
		System.out.println("testApproveFriendResult   DONE!");
		test.testDenyFriendResult();
		System.out.println("testDenyFriendResult      DONE!");
		test.testListMessageResult();
		System.out.println("testListMessageResult     DONE!");
		test.testRequestFriendResult();
		System.out.println("testRequestFriendResult   DONE!");
		test.testSendMessageResult();
		System.out.println("testSendMessageResult     DONE!");
	}
}
