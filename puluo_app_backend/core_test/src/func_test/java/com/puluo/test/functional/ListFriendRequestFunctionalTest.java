package com.puluo.test.functional;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.FriendRequestStatus;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class ListFriendRequestFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(ListFriendRequestFunctionalTest.class);
	static String mobile1 = "123456789";
	static String mobile2 = "234567890";
	static String mobile3 = "345678901";
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		PuluoFriendRequestDao requestDao = DaoApi.getInstance().friendRequestDao();
		userDao.save(mobile1, password);
		userDao.save(mobile2, password);
		userDao.save(mobile3, password);
		PuluoUser user1 = userDao.getByMobile(mobile1);
		PuluoUser user2 = userDao.getByMobile(mobile2);
		PuluoUser user3 = userDao.getByMobile(mobile3);
		String requestId = UUID.randomUUID().toString();
		requestDao.saveNewRequest(requestId, user2.userUUID(), user1.userUUID());
		requestDao.updateRequestStatus(requestId, FriendRequestStatus.Denied);
		requestDao.saveNewRequest(UUID.randomUUID().toString(), user2.userUUID(), user1.userUUID());
		requestDao.saveNewRequest(UUID.randomUUID().toString(), user3.userUUID(), user1.userUUID());
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
		PuluoFriendRequestDaoImpl requestDao = (PuluoFriendRequestDaoImpl) DaoApi.getInstance().friendRequestDao();
		PuluoPrivateMessageDaoImpl messagedao = (PuluoPrivateMessageDaoImpl) DaoApi.getInstance().privateMessageDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
	}
	
	@Test
	public void testRequestFriendFromFriend(){
		super.runAuthenticatedTest(new FriendRequestListFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/get", inputs(session));
				log.info(json);
				JsonNode requests = new JsonNode(getStringFromJson(json,"requests"));
				int size = requests.getArray().length();
				Assert.assertEquals("size should be 2", 2, size);
			}

			@Override
			public String inputs(String session) {
				return String.format("{\"token\":\"%s\"}", session);
			}
			
		});
	}
	
	abstract class FriendRequestListFunctionalTestRunner implements PuluoAuthenticatedFunctionalTestRunner {
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

