package com.puluo.test.functional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoPrivateMessageDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public class MessageFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(MessageFunctionalTest.class);
	static String mobile0 = "3344556677";
	static String uuid0;
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
		cleanupDB();
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		userDao.save(mobile0, password);
		uuid0 = userDao.getByMobile(mobile0).userUUID();
		userDao.save(mobile1, password);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		userDao.save(mobile2, password);
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		userDao.save(mobile3, password);
		uuid3 = userDao.getByMobile(mobile3).userUUID();
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoPrivateMessageDaoImpl messageDao = (PuluoPrivateMessageDaoImpl) DaoApi
				.getInstance().privateMessageDao();
		PuluoUser user;
		user = dao.getByMobile(mobile0);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}

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
		for (String msgId : msgIds) {
			messageDao.deleteByMsgUUID(msgId);
		}
		messageDao.deleteByMsgUUID("event1");
		messageDao.deleteByMsgUUID("event2");
		messageDao.deleteByMsgUUID("event3");
		messageDao.deleteByMsgUUID("event4");
	}

	@Test
	public void testListConversations() {
		log.info("testListConversations start!");
		try {
			String session0 = super.login(mobile0, password);
			PuluoPrivateMessageDao msgDao = DaoApi.getInstance()
					.privateMessageDao();

			PuluoPrivateMessageImpl msg1 = new PuluoPrivateMessageImpl(
					"event1", "uuid0 to uuid1 1", DateTime
					.now().minusDays(3), PuluoMessageType.TextMessage, "",
					uuid0, uuid1);
			msgDao.saveMessage(msg1);
			PuluoPrivateMessageImpl msg2 = new PuluoPrivateMessageImpl(
					"event2", "uuid1 to uuid0 2", DateTime
					.now().minusDays(2), PuluoMessageType.TextMessage, "",
					uuid1, uuid0);
			msgDao.saveMessage(msg2);
			PuluoPrivateMessageImpl msg3 = new PuluoPrivateMessageImpl(
					"event3", "uuid0 to uuid1 3", DateTime
					.now().minusDays(1), PuluoMessageType.TextMessage, "",
					uuid0, uuid1);
			msgDao.saveMessage(msg3);
			PuluoPrivateMessageImpl msg4 = new PuluoPrivateMessageImpl(
					"event4", "uuid1 to uuid0 4", DateTime
					.now(), PuluoMessageType.TextMessage, "",
					uuid1, uuid0);
			msgDao.saveMessage(msg4);

			String str = String
					.format("{\"token\":\"%s\",\"from_user_uuid\":\"%s\", \"to_user_uuid\":\"%s\"}",
							session0, uuid0, uuid1);
			JsonNode json1 = callAPI("users/messages", str);
			log.info(json1);
			JsonNode msgs1 = new JsonNode(super.getStringFromJson(json1,
					"messages"));
			int size1 = msgs1.getArray().length();
			Assert.assertEquals("size should be 4", 4, size1);
			List<String> actualIds = new ArrayList<String>();
			for (int i = 0; i < size1; i++) {
				JSONObject j = msgs1.getArray().getJSONObject(i);
				actualIds.add((String) j.get("msg_id"));
			}
			List<String> expectedIds = new ArrayList<String>();
			expectedIds.add(msg1.messageUUID());
			expectedIds.add(msg2.messageUUID());
			expectedIds.add(msg3.messageUUID());
			expectedIds.add(msg4.messageUUID());
			Assert.assertEquals(
					"returned message should be sorted by created_at asc",
					expectedIds, actualIds);

			long since = DateTime.now().minusDays(3).plusSeconds(1).getMillis();
			str = String.format("{\"token\":\"%s\","
					+ "\"from_user_uuid\":\"%s\", "
					+ "\"to_user_uuid\":\"%s\"," + "\"since\":%s}", session0,
					uuid0, uuid1, since);
			json1 = callAPI("users/messages", str);
			log.info(json1);
			msgs1 = new JsonNode(super.getStringFromJson(json1,
					"messages"));
			size1 = msgs1.getArray().length();
			Assert.assertEquals("size should be 3", 3, size1);
			actualIds = new ArrayList<String>();
			for (int i = 0; i < size1; i++) {
				JSONObject j = msgs1.getArray().getJSONObject(i);
				actualIds.add((String) j.get("msg_id"));
			}
			expectedIds = new ArrayList<String>();
			expectedIds.add(msg2.messageUUID());
			expectedIds.add(msg3.messageUUID());
			expectedIds.add(msg4.messageUUID());
			Assert.assertEquals(
					"returned message should be msg2,msg3,msg4 and sorted by created_at asc",
					expectedIds, actualIds);
			
			
			str = String.format("{\"token\":\"%s\","
					+ "\"from_user_uuid\":\"%s\", "
					+ "\"to_user_uuid\":\"%s\"," + "\"since\":%s,\"limit\":%s,\"offset\":%s}", 
					session0,uuid0, uuid1, since,1,0);
			json1 = callAPI("users/messages", str);
			log.info(json1);
			msgs1 = new JsonNode(super.getStringFromJson(json1,
					"messages"));
			size1 = msgs1.getArray().length();
			Assert.assertEquals("size should be 1", 1, size1);
			actualIds = new ArrayList<String>();
			for (int i = 0; i < size1; i++) {
				JSONObject j = msgs1.getArray().getJSONObject(i);
				actualIds.add((String) j.get("msg_id"));
			}
			expectedIds = new ArrayList<String>();
			expectedIds.add(msg2.messageUUID());
			Assert.assertEquals(
					"returned message should be msg2 and sorted by created_at asc",
					expectedIds, actualIds);
			
			str = String.format("{\"token\":\"%s\","
					+ "\"from_user_uuid\":\"%s\", "
					+ "\"to_user_uuid\":\"%s\"," + "\"since\":%s,\"limit\":%s,\"offset\":%s}", 
					session0,uuid0, uuid1, since,1,2);
			json1 = callAPI("users/messages", str);
			log.info(json1);
			msgs1 = new JsonNode(super.getStringFromJson(json1,
					"messages"));
			size1 = msgs1.getArray().length();
			Assert.assertEquals("size should be 1", 1, size1);
			actualIds = new ArrayList<String>();
			for (int i = 0; i < size1; i++) {
				JSONObject j = msgs1.getArray().getJSONObject(i);
				actualIds.add((String) j.get("msg_id"));
			}
			expectedIds = new ArrayList<String>();
			expectedIds.add(msg4.messageUUID());
			Assert.assertEquals(
					"returned message should be msg4 and sorted by created_at asc",
					expectedIds, actualIds);
			PuluoPrivateMessageDaoImpl md = (PuluoPrivateMessageDaoImpl)DaoApi.getInstance().privateMessageDao();
			md.deleteByMsgUUID("event1");
			md.deleteByMsgUUID("event2");
			md.deleteByMsgUUID("event3");
			md.deleteByMsgUUID("event4");
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListConversations done!");
	}

	@Test
	@Ignore("https://github.com/blshao84/puluo_sports/issues/24")
	public void testListMessageSummaryWithLimitOffset() {
		log.info("testListMessageSummaryWithLimitOffset start!");
		DateTime today = DateTime.now();
		PuluoPrivateMessage mgs1 = new PuluoPrivateMessageImpl("a", "msg1",
				today, PuluoMessageType.TextMessage, "", uuid1, uuid2);
		PuluoPrivateMessage mgs2 = new PuluoPrivateMessageImpl("b", "msg2",
				today.plusDays(1), PuluoMessageType.TextMessage, "", uuid2,
				uuid3);
		PuluoPrivateMessage mgs4 = new PuluoPrivateMessageImpl("c", "msg4",
				today, PuluoMessageType.TextMessage, "", uuid1, uuid3);
		PuluoPrivateMessageDao msgDao = DaoApi.getInstance()
				.privateMessageDao();
		msgDao.saveMessage(mgs1);
		msgDao.saveMessage(mgs2);
		msgDao.saveMessage(mgs4);
		msgIds.add(mgs1.messageUUID());
		msgIds.add(mgs2.messageUUID());
		msgIds.add(mgs4.messageUUID());

		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String.format(
					"{\"token\":\"%s\",\"limit\":%s,\"offset\":%s}", session,
					1, 1);
			JsonNode json = callAPI("users/messages/summary", str);
			log.info(json);
			ArrayList<JsonNode> msgs = super.getJsonArrayFromJson(json,
					"summaries");
			Assert.assertEquals("should return 1 msg", 1, msgs.size());
			JsonNode jmsg1 = msgs.get(0);
			String msgUUID1 = super.getStringFromJson(jmsg1,
					"last_message_uuid");
			Assert.assertEquals(mgs4.messageUUID(), msgUUID1);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessageSummaryWithLimitOffset done!");
	}

	@Test
	public void testListMessageSummary() {
		log.info("testListMessageSummary start!");
		DateTime today = DateTime.now();
		PuluoPrivateMessage mgs1 = new PuluoPrivateMessageImpl(UUID
				.randomUUID().toString(), "msg1", today,
				PuluoMessageType.TextMessage, "", uuid1, uuid2);
		PuluoPrivateMessage mgs2 = new PuluoPrivateMessageImpl(UUID
				.randomUUID().toString(), "msg2", today.plusDays(1),
				PuluoMessageType.TextMessage, "", uuid1, uuid2);
		PuluoPrivateMessage mgs3 = new PuluoPrivateMessageImpl(UUID
				.randomUUID().toString(), "msg3", today.minusDays(1),
				PuluoMessageType.TextMessage, "", uuid2, uuid1);
		PuluoPrivateMessage mgs4 = new PuluoPrivateMessageImpl(UUID
				.randomUUID().toString(), "msg4", today,
				PuluoMessageType.TextMessage, "", uuid1, uuid3);
		PuluoPrivateMessageDao msgDao = DaoApi.getInstance()
				.privateMessageDao();
		msgDao.saveMessage(mgs1);
		msgDao.saveMessage(mgs2);
		msgDao.saveMessage(mgs3);
		msgDao.saveMessage(mgs4);
		msgIds.add(mgs1.messageUUID());
		msgIds.add(mgs2.messageUUID());
		msgIds.add(mgs3.messageUUID());
		msgIds.add(mgs4.messageUUID());

		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String.format("{\"token\":\"%s\"}", session);
			JsonNode json = callAPI("users/messages/summary", str);
			log.info(json);
			ArrayList<JsonNode> msgs = super.getJsonArrayFromJson(json,
					"summaries");
			Assert.assertEquals("should return 2 msg", 2, msgs.size());
			JsonNode jmsg1 = msgs.get(0);
			JsonNode jmsg2 = msgs.get(1);
			String msgUUID1 = super.getStringFromJson(jmsg1,
					"last_message_uuid");
			String msgUUID2 = super.getStringFromJson(jmsg2,
					"last_message_uuid");
			Set<String> expected = new HashSet<String>();
			Set<String> actual = new HashSet<String>();
			expected.add(mgs2.messageUUID());
			expected.add(mgs4.messageUUID());
			actual.add(msgUUID1);
			actual.add(msgUUID2);
			Assert.assertEquals("", expected, actual);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessageSummary done!");
	}

	@Test
	public void testNotExistingToUUID() {
		log.info("testNotExistingToUUID start!");
		try {
			String session = super.login(mobile1, password);
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String
					.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid3, "", "");
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String
					.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, PuluoMessageType.TextMessage.name());
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String
					.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, "Hello, Puluo!",
							PuluoMessageType.TextMessage.name());
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String
					.format("{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, "Hello, Puluo!",
							PuluoMessageType.ImageMessage.name());
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
					.getInstance().sessionDao();
			DateTime now = sessionDao.now();

			String str = String
					.format("{\"token\":\"%s\", \"from_user_uuid\":\"%s\",\"user_uuid\":\"%s\", \"since\":\"%s\"}",
							session, uuid2, uuid4, now.getMillis() - 10000);
			JsonNode json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json,
					"messages"));
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			JsonNode json;

			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, "1: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, "2: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid2, "3: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));

			String str = String
					.format("{\"token\":\"%s\",\"from_user_uuid\":\"%s\", \"to_user_uuid\":\"%s\"}",
							session, uuid3, uuid2);
			json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json,
					"messages"));
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			String str = String
					.format("{\"token\":\"%s\",\"from_user_uuid\":\"%s\", \"to_user_uuid\":\"%s\"}",
							session, uuid2, uuid4);
			JsonNode json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json,
					"messages"));
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
			Assert.assertFalse(
					"successful login should give not null session id",
					Strs.isEmpty(session));

			PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
					.getInstance().sessionDao();
			DateTime now = sessionDao.now();
			JsonNode json;

			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid1, "1: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid1, "2: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));
			json = callAPI(
					"users/message/send",
					String.format(
							"{\"token\":\"%s\", \"to_uuid\":\"%s\", \"content\":\"%s\", \"content_type\":\"%s\"}",
							session, uuid1, "3: Hello, Puluo!",
							PuluoMessageType.TextMessage.name()));
			msgIds.add(super.getStringFromJson(json, "msg_id"));

			String str = String
					.format("{\"token\":\"%s\", \"from_user_uuid\":\"%s\", \"to_user_uuid\":\"%s\", \"since\":\"%s\"}",
							session, uuid2, uuid1, now.getMillis() - 10000);
			json = callAPI("users/messages", str);
			log.info(json);
			JsonNode msgs = new JsonNode(super.getStringFromJson(json,
					"messages"));
			log.info(msgs.getArray().length());
			Assert.assertEquals("size should be 3", 3, msgs.getArray().length());
		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
		log.info("testListMessagesBasic done!");
	}
}
