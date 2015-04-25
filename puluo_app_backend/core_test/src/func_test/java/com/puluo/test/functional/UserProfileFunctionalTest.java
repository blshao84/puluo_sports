package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.PuluoUser;
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
	
	static String mobile3 = "345678901";
	static String uuid3 = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
	static String sessionId;

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		dao.save(mobile1, password1);
		PuluoUser user1 = dao.getByMobile(mobile1);
		dao.updateProfile(user1, "user1", "user1", "http://upyun.com/puluo/user1.jpg", "http://upyun.com/puluo/user1.jpg",
				"i am user1","user1@puluo.com","M", null,
				null, null, null, null);
		uuid1 = user1.userUUID();
		
		dao.save(mobile2, password2);
		PuluoUser user2 = dao.getByMobile(mobile2);
		dao.updateProfile(user2, "user2", "user2", "http://upyun.com/puluo/user2.jpg", "http://upyun.com/puluo/user2.jpg",
				"i am user2","user2@puluo.com","M", null,
				null, null, null, null);
		uuid2 = user2.userUUID();
		
		
		dao.save(mobile4, password4);
		PuluoUser user4 = dao.getByMobile(mobile4);
		dao.updateProfile(user4, "user4", "user4", "http://upyun.com/puluo/user4.jpg", "http://upyun.com/puluo/user4.jpg",
				"i am user4","user4@puluo.com","M", null,
				null, null, null, null);
		uuid4 = user4.userUUID();
		
		DaoApi.getInstance().friendshipDao().addOneFriend(uuid1, uuid4);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoUserFriendshipDaoImpl friendDao = (PuluoUserFriendshipDaoImpl) DaoApi.getInstance().friendshipDao();
		
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
		friendDao.deleteByUserUUID(uuid1);
		friendDao.deleteByUserUUID(uuid4);
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
				String f1 = getStringFromJson(json1,"public_info","following");
				String f2 = getStringFromJson(json2,"public_info","following");
				String f4 = getStringFromJson(json4,"public_info","following");
				Assert.assertEquals("mobile1 should be friend of himself", "true",f1);
				Assert.assertEquals("mobile1 should be friend of mobile4", "true",f4);
				Assert.assertEquals("mobile1 should NOT be friend of mobile2", "false",f2);
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
