package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class DeleteFriendFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(DeleteFriendFunctionalTest.class);
	static String mobile1 = "123456789";
	static String mobile2 = "234567890";
	static String mobile3 = "345678901";
	static String uuid3;
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		PuluoUserFriendshipDao friendDao = DaoApi.getInstance().friendshipDao();
		userDao.save(mobile1, password);
		userDao.save(mobile2, password);
		userDao.save(mobile3, password);
		PuluoUser user1 = userDao.getByMobile(mobile1);
		PuluoUser user2 = userDao.getByMobile(mobile2);
		PuluoUser user3 = userDao.getByMobile(mobile3);
		uuid3 = user3.userUUID();
		friendDao.addOneFriend(user1.userUUID(), user2.userUUID());
		friendDao.addOneFriend(user1.userUUID(), user3.userUUID());
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoUserFriendshipDaoImpl friendshipDao = (PuluoUserFriendshipDaoImpl) DaoApi.getInstance().friendshipDao();
		PuluoFriendRequestDaoImpl requestDao = (PuluoFriendRequestDaoImpl) DaoApi.getInstance().friendRequestDao();
		PuluoPrivateMessageDaoImpl messagedao = (PuluoPrivateMessageDaoImpl) DaoApi.getInstance().privateMessageDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
	}
	
	@Test
	public void testRequestFriendFromFriend(){
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/delete", inputs(session));
				log.info(json);
				JsonNode friends = new JsonNode(getStringFromJson(json, "friends"));
				Assert.assertEquals("size should be 1", 1, friends.getArray().length());
				String friend = friends.getArray().get(0).toString();
				Assert.assertEquals("uuid should be " + uuid3, uuid3, friend);
			}

			@Override
			public String inputs(String session) {
				String uuid = DaoApi.getInstance().userDao().getByMobile(mobile2).userUUID();
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session,uuid);
			}
			
		});
	}
	
	abstract class FriendRequestFunctionalTestRunner implements PuluoAuthenticatedFunctionalTestRunner {
		@Override
		public String mobile() {
			return mobile1;
		}

		@Override
		public String password() {
			return password;
		}
	}
}

