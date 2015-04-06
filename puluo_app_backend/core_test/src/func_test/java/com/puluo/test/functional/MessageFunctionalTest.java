package com.puluo.test.functional;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class MessageFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(MessageFunctionalTest.class);
	static String mobile1 = "123456789";
	static String uuid1;
	static String mobile2 = "234567890";
	static String uuid2;
	static String mobile3 = "345678901";
	static String uuid3;
	static String mobile4 = "456789012";
	static String uuid4 = "88d8e55d-bc48-4471-bf1a-a0d7066cd8b2";
	static String password = "abcdefg";
	static List<String> msgIds = new ArrayList<String>();

	@BeforeClass
	public static void setupDB() {
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		userDao.save(mobile1, password);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		userDao.save(mobile2, password);
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		userDao.save(mobile3, password);
		uuid3 = userDao.getByMobile(mobile3).userUUID();
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance().userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
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
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
		for (String msgId: msgIds) {
			messageDao.deleteByMsgUUID(msgId);
		}
	}
	
	@Test
	public void testNotExistingToUUID() {
		log.info("testNotExistingToUUID start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid3, "", "");
			JsonNode json = callAPI("users/message/send", str);
			log.info(json);
			String error = super.getStringFromJson(json, "id");
			Assert.assertEquals("error id should be 31", "31", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testNotExistingToUUID done!");
	}
	
	@Test
	public void testNullContent() {
		log.info("testNullContent start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, PuluoMessageType.TextMessage.name());
			JsonNode json = callAPI("users/message/send", str);
			log.info(json);
			String error = super.getStringFromJson(json, "id");
			Assert.assertEquals("error id should be 15", "15", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testNullContent done!");
	}
	
	@Test
	public void testSendMessage() {
		log.info("testSendMessage start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, "Hello, Puluo!", PuluoMessageType.TextMessage.name());
			JsonNode json = callAPI("users/message/send", str);
			log.info(json);
			String to_uuid = super.getStringFromJson(json, "to_user");
			Assert.assertEquals("to_uuid should be " + uuid2, uuid2, to_uuid);
			msgIds.add(super.getStringFromJson(json, "msg_id"));
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testSendMessage done!");
	}
	
	@Test
	public void testOtherContentTypes() {
		log.info("testOtherContentTypes start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, "Hello, Puluo!", PuluoMessageType.ImageMessage.name());
			JsonNode json = callAPI("users/message/send", str);
			log.info(json);
			String error = super.getStringFromJson(json, "id");
			Assert.assertEquals("error id should be 31", "31", error);
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testOtherContentTypes done!");
	}
	
	@Test
	public void testListMessagesNotExistingUUID() {
		log.info("testListMessagesNotExistingUUID start!");
		try {
			String session = super.login(mobile2, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
			DateTime now = sessionDao.now();
			
			String str = String.format("{\"token\":\"%s\", \"user_uuid\":\"%s\", \"since\":\"%s\"}", session, uuid4, now.getMillis() - 10000);
			JsonNode json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json, "messages"));
			log.info(msgs.getArray().length());
			Assert.assertEquals("size should be 0", 0, msgs.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessagesNotExistingUUID done!");
	}
	
	@Test
	public void testListMessagesWithHistory() {
		log.info("testListMessagesWithHistory start!");
		try {
			String session = super.login(mobile3, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			JsonNode json;

			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, "1: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, "2: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid2, "3: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			
			String str = String.format("{\"token\":\"%s\", \"user_uuid\":\"%s\"}", session, uuid2);
			json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json, "messages"));
			log.info(msgs.getArray().length());
			Assert.assertEquals("size should be 3", 3, msgs.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessagesWithHistory done!");
	}
	
	@Test
	public void testListMessagesWithoutHistory() {
		log.info("testListMessagesWithoutHistory start!");
		try {
			String session = super.login(mobile2, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			String str = String.format("{\"token\":\"%s\", \"user_uuid\":\"%s\"}", session, uuid3);
			JsonNode json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json, "messages"));
			log.info(msgs.getArray().length());
			Assert.assertEquals("size should be 0", 0, msgs.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessagesWithoutHistory done!");
	}
	
	@Test
	public void testListMessagesBasic() {
		log.info("testListMessagesBasic start!");
		try {
			String session = super.login(mobile2, password);
			Assert.assertFalse("successful login should give not null session id", Strs.isEmpty(session));
			
			PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi.getInstance().sessionDao();
			DateTime now = sessionDao.now();
			JsonNode json;

			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid1, "1: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid1, "2: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI("users/message/send", String.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}", session, uuid1, "3: Hello, Puluo!", PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			
			String str = String.format("{\"token\":\"%s\", \"user_uuid\":\"%s\", \"since\":\"%s\"}", session, uuid1, now.getMillis() - 10000);
			json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json, "messages"));
			log.info(msgs.getArray().length());
			Assert.assertEquals("size should be 3", 3, msgs.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessagesBasic done!");
	}
}
