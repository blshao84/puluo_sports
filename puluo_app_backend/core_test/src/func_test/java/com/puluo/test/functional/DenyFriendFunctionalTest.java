package com.puluo.test.functional;

import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class DenyFriendFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(DenyFriendFunctionalTest.class);
	static String mobile1 = "123456789";
	static String uuid1;
	static String mobile2 = "234567890";
	static String uuid2;
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		PuluoFriendRequestDao requestDao = DaoApi.getInstance().friendRequestDao();
		userDao.save(mobile1, password);
		userDao.save(mobile2, password);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		requestDao.saveNewRequest(UUID.randomUUID().toString(), uuid1, uuid2);
		requestDao.saveNewRequest(UUID.randomUUID().toString(), uuid1, uuid2);
		requestDao.saveNewRequest(UUID.randomUUID().toString(), uuid2, uuid1);
		requestDao.saveNewRequest(UUID.randomUUID().toString(), uuid2, uuid1);
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
	}
	
	@Test
	public void testApproveFriendFromFriend(){
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/deny", inputs(session));
				log.info(json);
				PuluoUserFriendshipDao friendshipdao = DaoApi.getInstance().friendshipDao();
				Assert.assertFalse("they should not be friends", friendshipdao.isFriend(uuid1, uuid2));
				PuluoFriendRequestDao friendRequestDao = DaoApi.getInstance().friendRequestDao();
				List<PuluoFriendRequest> requests = friendRequestDao.getFriendRequestByUsers(uuid1,uuid2,FriendRequestStatus.Denied);
				PuluoFriendRequest request =  requests.get(0);
				requests.addAll(friendRequestDao.getFriendRequestByUsers(uuid2,uuid1,FriendRequestStatus.Denied));
				Assert.assertEquals("size should be 4", 4, requests.size());
				List<PuluoPrivateMessage> message = request.messages();
				Assert.assertEquals("receiver's uuid should be " + uuid1, uuid1, message.get(0).fromUser().userUUID());
				Assert.assertEquals("requestor's uuid should be " + uuid2, uuid2, message.get(0).toUser().userUUID());
			}

			@Override
			public String inputs(String session) {
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session,uuid2);
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

