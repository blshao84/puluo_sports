package com.puluo.test.functional;

import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoFriendRequestDaoImpl;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.dao.impl.PuluoUserFriendshipDaoImpl;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class RequestFriendFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(RequestFriendFunctionalTest.class);
	static String mobile1 = "123456789";
	static String mobile2 = "234567890";
	static String mobile3 = "345678901";
	static String mobile4 = "456789012";
	static String mobile5 = "567890123";
	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoUserDao userDao = DaoApi.getInstance().userDao();
		PuluoUserFriendshipDao friendDao = DaoApi.getInstance().friendshipDao();
		PuluoFriendRequestDao requestDao = DaoApi.getInstance().friendRequestDao();
		userDao.save(mobile1, password);
		userDao.save(mobile2, password);
		userDao.save(mobile3, password);
		userDao.save(mobile4, password);
		userDao.save(mobile5, password);
		PuluoUser user1 = userDao.getByMobile(mobile1);
		PuluoUser user2 = userDao.getByMobile(mobile2);
		PuluoUser user4 = userDao.getByMobile(mobile4);
		PuluoUser user5 = userDao.getByMobile(mobile5);
		friendDao.addOneFriend(user1.userUUID(), user2.userUUID());
		requestDao.saveNewRequest(UUID.randomUUID().toString(), user4.userUUID(), user1.userUUID());
		requestDao.saveNewRequest(UUID.randomUUID().toString(), user1.userUUID(), user5.userUUID());
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
		user = dao.getByMobile(mobile4);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			friendshipDao.deleteByUserUUID(user.userUUID());
			requestDao.deleteByUserUUID(user.userUUID());
			messagedao.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile5);
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
				JsonNode json = callAPI("/users/friends/request/send", inputs(session));
				log.info(json);
				String error = getStringFromJson(json, "id");
				Assert.assertEquals("error id should be 33", "33", error);
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
	
	@Test
	public void testRequestFriendFromNotExistingUser(){
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/send", inputs(session));
				log.info(json);
				String error = getStringFromJson(json, "id");
				Assert.assertEquals("error id should be 32", "32", error);
			}

			@Override
			public String inputs(String session) {
				String uuid = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session,uuid);
			}
			
		});
	}
	
	@Test
	public void testRequestFriendFromStrangerWithExistingRequestFromStranger(){
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				//mobile1和mobile4不是好友
				//存在一条由mobile4到mobile1得request,下面测试当这个request在不同状态时，mobile1向mobile4发送好友请求
				//case 1: req的状态为Requested
				JsonNode json1 = runWithRequestStatus(FriendRequestStatus.Requested,session);
				log.info(json1);
				String error1 = getStringFromJson(json1, "id");
				Assert.assertEquals("error id should be 42", "42", error1);
				
				//case 2: req的状态为Approved：这种情况可能是mobile1和mobile4曾经是好友，但好友关系被移除以后重新申请
				JsonNode json2 = runWithRequestStatus(FriendRequestStatus.Approved,session);
				System.out.println(json2);
				
				//case 2: req的状态为Denied
				JsonNode json3 = runWithRequestStatus(FriendRequestStatus.Denied,session);
				System.out.println(json3);
			}

			@Override
			public String inputs(String session) {
				String uuid = DaoApi.getInstance().userDao().getByMobile(mobile4).userUUID();
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session,uuid);
			}
			
			private JsonNode runWithRequestStatus(FriendRequestStatus status,String session) throws UnirestException {
				PuluoFriendRequestDao reqDao = DaoApi.getInstance().friendRequestDao();
				PuluoUserDao userDao = DaoApi.getInstance().userDao();
				String uuid1 = userDao.getByMobile(mobile1).userUUID();
				String uuid4 = userDao.getByMobile(mobile4).userUUID();
				PuluoFriendRequest req = reqDao.getFriendRequestByUsers(uuid4, uuid1).get(0);
				reqDao.updateRequestStatus(req.requestUUID(), status);
				return callAPI("/users/friends/request/send", inputs(session));
			}
			
		});
	}
	
	@Test
	public void testRequestFriendFromStrangerWithExistingRequestToStranger(){
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/send", inputs(session));
				log.info(json);
				String error = getStringFromJson(json, "id");
				Assert.assertEquals("error id should be 34", "34", error);
			}

			@Override
			public String inputs(String session) {
				String uuid = DaoApi.getInstance().userDao().getByMobile(mobile5).userUUID();
				return String.format("{" + "\"token\":\"%s\","
						+ "\"user_uuid\":\"%s\""
						+ "}", session,uuid);
			}
			
		});
	}

	@Test
	public void testRequestFriendFromStranger() {
		super.runAuthenticatedTest(new FriendRequestFunctionalTestRunner() {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("/users/friends/request/send", inputs(session));
				log.info(json);
				String reqUUID = getStringFromJson(json,"friend_request","request_id");
				JSONObject jsonReq = json.getObject().getJSONObject("friend_request");
				JSONObject jsonMsg = jsonReq.getJSONArray("messages").getJSONObject(0);
				String fromUUID = extractStringFromJSON(jsonMsg,"from_user");
				String toUUID = extractStringFromJSON(jsonMsg,"to_user");
				String msgUUID = extractStringFromJSON(jsonMsg,"msg_id");
				log.info(String.format("req_uuid=%s,from_uuid=%s,to_uuid=%s,msg_uuid=%s",reqUUID,fromUUID,toUUID,msgUUID));
				PuluoDSI dsi = DaoApi.getInstance();
				PuluoFriendRequest req = dsi.friendRequestDao().findByUUID(reqUUID);
				PuluoUser fromUser = dsi.userDao().getByUUID(fromUUID);
				PuluoUser toUser = dsi.userDao().getByUUID(toUUID);
				PuluoPrivateMessage msg = dsi.privateMessageDao().findByUUID(msgUUID);
				Assert.assertNotNull(req);
				Assert.assertNotNull(fromUser);
				Assert.assertNotNull(toUser);
				Assert.assertNotNull(msg);
				Assert.assertNotNull(msg.friendRequest());
				Assert.assertEquals("msg's friend request should match the returned req",req.requestUUID(),msg.friendRequest().requestUUID());
				Assert.assertEquals("msg's from_user should match the returned from user",fromUUID,msg.fromUser().userUUID());
				Assert.assertEquals("msg's to_user should match the returned from user",toUUID,msg.toUser().userUUID());
				List<PuluoPrivateMessage> msgs = req.messages();
				Assert.assertEquals("this req should only return 1 msg", 1,msgs.size());
				PuluoPrivateMessage reqMsg = msgs.get(0);
				Assert.assertEquals("req's message should match the returned msg", msgUUID,reqMsg.messageUUID());
			}

			@Override
			public String inputs(String session) {
				String uuid = DaoApi.getInstance().userDao().getByMobile(mobile3).userUUID();
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

