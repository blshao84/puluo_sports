package com.puluo.test.functional;

import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserBlacklistDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserProfileFunctionalTest extends APIFunctionalTest {

	public static Log log = LogFactory.getLog(UserProfileFunctionalTest.class);

	static String mobile1 = "123456789";
	static String uuid1;
	static String password1 = "abcdefg";
	static String mobile2 = "234567890";
	static String uuid2;
	static String password2 = "bcdefgh";

	static String mobile4 = "4532342367890";
	static String uuid4;
	static String password4 = "bcdefgh";
	
	static String mobile5 = "6872342367890";
	static String uuid5;
	static String password5 = "bcdefgh";
	
	static String mobile3 = "345678901";
	static String uuid3 = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
	static String sessionId;
	
	static String mobile6 = "456789012";
	static String uuid6;
	static String password6 = "bcdefgh";
	
	static String requestId1 = UUID.randomUUID().toString();
	static String requestId2 = UUID.randomUUID().toString();

	static String messageId = UUID.randomUUID().toString();

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		dao.save(mobile1, password1);
		PuluoUser user1 = dao.getByMobile(mobile1);
		dao.updateProfile(user1, "user1", "user1", "http://upyun.com/puluo/user1.jpg",
				"i am user1","user1@puluo.com","M", null,
				null, null, null, null);
		uuid1 = user1.userUUID();
		
		dao.save(mobile2, password2);
		PuluoUser user2 = dao.getByMobile(mobile2);
		dao.updateProfile(user2, "user2", "user2", "http://upyun.com/puluo/user2.jpg", 
				"i am user2","user2@puluo.com","M", null,
				null, null, null, null);
		uuid2 = user2.userUUID();
		
		dao.save(mobile4, password4);
		PuluoUser user4 = dao.getByMobile(mobile4);
		dao.updateProfile(user4, "user4", "user4", "http://upyun.com/puluo/user4.jpg", 
				"i am user4","user4@puluo.com","M", null,
				null, null, null, null);
		uuid4 = user4.userUUID();
		
		dao.save(mobile5, password5);
		PuluoUser user5 = dao.getByMobile(mobile5);
		dao.updateProfile(user5, "user5", "user5", "http://upyun.com/puluo/user5.jpg", 
				"i am user5","user5@puluo.com","M", null,
				null, null, null, null);
		uuid5 = user5.userUUID();
		
		dao.save(mobile6, password6);
		PuluoUser user6 = dao.getByMobile(mobile6);
		dao.updateProfile(user6, "user6", "user6", "http://upyun.com/puluo/user6.jpg", 
				"i am user6","user6@puluo.com","M", null,
				null, null, null, null);
		uuid6 = user6.userUUID();
		
		DaoApi.getInstance().friendshipDao().addOneFriend(uuid1, uuid4);

		DaoApi.getInstance().friendRequestDao().saveNewRequest(requestId1, uuid2, uuid1);
		DaoApi.getInstance().friendRequestDao().saveNewRequest(requestId2, uuid5, uuid1);
		
		DaoApi.getInstance().privateMessageDao().saveMessage(new PuluoPrivateMessageImpl(messageId, "Hello, User1!", DateTime.now(), PuluoMessageType.FriendRequest, requestId1, uuid2, uuid1));
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoUserFriendshipDaoImpl friendDao = (PuluoUserFriendshipDaoImpl) DaoApi.getInstance().friendshipDao();
		PuluoFriendRequestDaoImpl requestDao = (PuluoFriendRequestDaoImpl) DaoApi.getInstance().friendRequestDao();
		PuluoPrivateMessageDaoImpl messageDao = (PuluoPrivateMessageDaoImpl) DaoApi.getInstance().privateMessageDao();
		
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}

		user = dao.getByMobile(mobile4);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		user = dao.getByMobile(mobile5);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		user = dao.getByMobile(mobile6);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		friendDao.deleteByUserUUID(uuid1);
		friendDao.deleteByUserUUID(uuid4);

		requestDao.deleteByReqUUID(requestId1);
		requestDao.deleteByReqUUID(requestId2);
		
		messageDao.deleteByMsgUUID(messageId);
		PuluoUserBlacklistDaoImpl blacklistDao = (PuluoUserBlacklistDaoImpl)DaoApi.getInstance().blacklistDao();
		blacklistDao.deleteByUserUUID(uuid1);
	}

	@Test
	public void testFollowingInUserProfile() {
		log.info("public_info.following should return whether the user requests api is a friend of the user being requested");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json2 = callAPI("users/profile/" + mobile2,
						inputs(session));
				log.info(json2);
				JsonNode json1 = callAPI("users/profile/" + mobile1,
						inputs(session));
				log.info(json1);
				JsonNode json4 = callAPI("users/profile/" + mobile4,
						inputs(session));
				log.info(json4);
				JsonNode json6 = callAPI("users/profile/" + mobile6,
						inputs(session));
				log.info(json6);
				String f1 = getStringFromJson(json1,"public_info","following");
				String f2 = getStringFromJson(json2,"public_info","following");
				String f4 = getStringFromJson(json4,"public_info","following");
				String f6 = getStringFromJson(json6,"public_info","following");
				Assert.assertEquals("mobile1 should be friend of himself", "true",f1);
				Assert.assertEquals("mobile1 should be friend of mobile4", "true",f4);
				Assert.assertEquals("mobile1 should be in pending status with mobile2", "pending",f2);
				Assert.assertEquals("mobile1 should NOT be friend of mobile6", "false",f6);
			}
		});
	}
	@Test
	public void testExistingMobile() {
		log.info("testExistingMobile start!");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + mobile1,
						inputs(session));
				log.info(json);
				String uuid = getStringFromJson(json, "uuid");
				String email = getStringFromJson(json, "private_info","email");
				String saying = getStringFromJson(json, "public_info","saying");
				Assert.assertEquals("mobile1 should get his own private info, expect email to be non-empty","user1@puluo.com", email);
				Assert.assertEquals("should return user's saying ", "i am user1", saying);
				Assert.assertEquals("uuid should be " + uuid1, uuid1, uuid);
				
				JsonNode private_info = new JsonNode(getStringFromJson(json, "private_info"));
				List<JsonNode> pendings = getJsonArrayFromJson(private_info, "pending");
				Assert.assertEquals(requestId2 + " should be the first of the listed pending request", requestId2, getStringFromJson(pendings.get(0), "friend_request","request_id"));
				Assert.assertEquals(requestId1 + " should be the first of the listed pending request", requestId1, getStringFromJson(pendings.get(1), "friend_request","request_id"));
				List<JsonNode> messages2 = getJsonArrayFromJson(new JsonNode(getStringFromJson(pendings.get(0), "friend_request")), "messages");
				Assert.assertTrue("0 message should exist", 0==messages2.size());
				List<JsonNode> messages1 = getJsonArrayFromJson(new JsonNode(getStringFromJson(pendings.get(1), "friend_request")), "messages");
				Assert.assertTrue("1 message should exist", 1==messages1.size());
			}

		});
		log.info("testExistingMobile done!");
	}

	@Test
	public void testExistingUUID() {
		log.info("testExistingUUID start!");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + uuid2,
						inputs(session));
				log.info(json);
				String uuid = getStringFromJson(json, "uuid");
				Assert.assertEquals("uuid should be " + uuid2, uuid2, uuid);
			}

		});
		log.info("testExistingUUID done!");
	}

	@Test
	public void testNotExistingMobile() {
		log.info("testNotExistingMobile start!");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + mobile3,
						inputs(session));
				log.info(json);
				String error = getStringFromJson(json, "id");
				log.info("error code = " + error);
				Assert.assertEquals("error id should be 17", "17", error);
			}
		});
		log.info("testNotExistingMobile done!");
	}

	@Test
	public void testNotExistingUUID() {
		log.info("testNotExistingUUID start!");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + uuid3,
						inputs(session));
				log.info(json);
				String error = getStringFromJson(json, "id");
				log.info("error code = " + error);
				Assert.assertEquals("error id should be 17", "17", error);

			}
		});

		log.info("testNotExistingUUID done!");
	}

	@Test
	public void testNonLoginUserProfile() {
		log.info("the login user get other user's profile");
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + mobile2,
						inputs(session));
				log.info(json);
				String uuid = getStringFromJson(json, "uuid");
				String email = getStringFromJson(json, "private_info","email");
				String saying = getStringFromJson(json, "public_info","saying");
				Assert.assertEquals("should return user's saying ", "i am user2", saying);
				Assert.assertEquals("mobile1 should not get mobile2's email, expect empty email field ", "", email);
				Assert.assertEquals("uuid should be " + uuid2, uuid2, uuid);
			}

		});

	}
	
	@Test
	public void testBannedUser() {
		super.runAuthenticatedTest(new UserProfileFunctionalTestRunner() {
			
			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/profile/" + mobile2,
						inputs(session));
				log.info(json);
				String banned = getStringFromJson(json, "public_info","banned");
				Assert.assertEquals("false", banned);
				String input = String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session,uuid2);
				callAPI("users/blacklist/ban", input);
				json = callAPI("users/profile/" + mobile2,
						inputs(session));
				banned = getStringFromJson(json, "public_info","banned");
				Assert.assertEquals("true", banned);
				
			}
		});
	}

	abstract class UserProfileFunctionalTestRunner implements
			PuluoAuthenticatedFunctionalTestRunner {

		@Override
		public String mobile() {
			return mobile1;
		}

		@Override
		public String password() {
			return password1;
		}

		@Override
		public String inputs(String session) {
			return String.format("{\"token\":\"%s\"}", session);
		}

	}

}
