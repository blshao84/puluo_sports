package com.puluo.test.dao;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.impl.PuluoPrivateMessageImpl;
import com.puluo.enumeration.PuluoMessageType;
import com.puluo.enumeration.SortDirection;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class PuluoPrivateMessageDaoTest {
	public static Log log = LogFactory.getLog(PuluoPrivateMessageDaoTest.class);

	public static String user1;
	public static String user2;
	public static String user3;

	@BeforeClass
	public static void setUpDB() {
		try{
			cleanUpDB();
		}catch(Exception e){
			
		}
		log.info("setUpDB start!");
		
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save("31", "111111");
		userDao.save("32", "222222");
		userDao.save("33", "333333");
		user1 = userDao.getByMobile("31").userUUID();
		user2 = userDao.getByMobile("32").userUUID();
		user3 = userDao.getByMobile("33").userUUID();
		
		PuluoPrivateMessageDao messageDao = DaoTestApi.privateMessageDevDao;
		messageDao.createTable();
		PuluoPrivateMessage msg1 = new PuluoPrivateMessageImpl("1", "11",
				TimeUtils.parseDateTime("2015-03-25 12:35:59"),
				PuluoMessageType.FriendRequest, "21", user1, user2,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg1);
		PuluoPrivateMessage msg2 = new PuluoPrivateMessageImpl("2", "21",
				TimeUtils.parseDateTime("2015-03-26 12:35:59"),
				PuluoMessageType.FriendRequest, "21", user1, user2,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg2);
		PuluoPrivateMessage msg3 = new PuluoPrivateMessageImpl("3", "31",
				TimeUtils.parseDateTime("2015-03-27 12:35:59"),
				PuluoMessageType.TextMessage, "21", user1, user3,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg3);
		PuluoPrivateMessage msg4 = new PuluoPrivateMessageImpl("4", "41",
				TimeUtils.parseDateTime("2015-03-28 12:35:59"),
				PuluoMessageType.TextMessage, "21", user2, user3,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg4);
		PuluoPrivateMessage msg5 = new PuluoPrivateMessageImpl("5", "41",
				TimeUtils.parseDateTime("2015-03-29 12:35:59"),
				PuluoMessageType.TextMessage, "21", user2, user3,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg5);
		PuluoPrivateMessage msg6 = new PuluoPrivateMessageImpl("6", "41",
				TimeUtils.parseDateTime("2015-03-23 12:35:59"),
				PuluoMessageType.TextMessage, "21", user2, user1,
				DaoTestApi.getInstance());
		messageDao.saveMessage(msg6);
		
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		
		DalTemplate userDao = (DalTemplate) DaoTestApi.userDevDao;
		userDao.getWriter().execute("drop table " + userDao.getFullTableName());
		log.info("dropped table " + userDao.getFullTableName());
		
		DalTemplate messageDao = (DalTemplate) DaoTestApi.privateMessageDevDao;
		messageDao.getWriter().execute("drop table " + messageDao.getFullTableName());
		log.info("dropped table " + messageDao.getFullTableName());
		
		log.info("cleanUpDB done!");
	}

	@Test
	public void testSaveDupMessage() {
		log.info("testSaveDupMessage start!");

		PuluoPrivateMessageDao messageDao = DaoTestApi.privateMessageDevDao;
		PuluoPrivateMessage msg1 = new PuluoPrivateMessageImpl("1", "11",
				TimeUtils.parseDateTime("2015-03-25 12:35:59"),
				PuluoMessageType.FriendRequest, "21", user1, user2,
				DaoTestApi.getInstance());
		boolean success = messageDao.saveMessage(msg1);
		Assert.assertFalse("save a message should fail!", success);
		
		log.info("testSaveDupMessage done!");
	}

	@Test
	public void testGetFriendRequestMessage() {
		log.info("testGetFriendRequestMessage start!");

		PuluoPrivateMessageDao messageDao = DaoTestApi.privateMessageDevDao;
		List<PuluoPrivateMessage> messages = messageDao.getFriendRequestMessage(user1, user2);
		Assert.assertEquals("messages' size should be 2", 2, messages.size());
		Assert.assertEquals("message uuid should be 2", "2", messages.get(0).messageUUID());
		Assert.assertEquals("message uuid should be 1", "1", messages.get(1).messageUUID());
		
		log.info("testGetFriendRequestMessage done!");
	}

	@Test
	public void testGetMessagesByFromUser() {
		log.info("testGetMessagesByFromUser start!");
		
		PuluoPrivateMessageDao messageDao = DaoTestApi.privateMessageDevDao;
		List<PuluoPrivateMessage> messages = messageDao.getMessagesByFromUser(user2, null, null,0,0);
		Assert.assertEquals("messages' size should be 3", 3, messages.size());
		messages = messageDao.getMessagesByFromUser(user2, TimeUtils.parseDateTime("2015-03-28 08:35:59"), null,0,0);
		Assert.assertEquals("messages' size should be 2", 2, messages.size());
		messages = messageDao.getMessagesByFromUser(user2, null, TimeUtils.parseDateTime("2015-03-28 08:35:59"),0,0);
		Assert.assertEquals("messages' size should be 1", 1, messages.size());
		
		log.info("testGetMessagesByFromUser done!");
	}

	@Test
	public void testGetMessagesByUser() {
		log.info("testGetMessagesByUser start!");
		
		PuluoPrivateMessageDao messageDao = DaoTestApi.privateMessageDevDao;
		List<PuluoPrivateMessage> messages = messageDao.getMessagesByUser(null, null, null, null,0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 4", 4, messages.size());
		messages = messageDao.getMessagesByUser(user2, null, null, null,0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 3", 3, messages.size());
		messages = messageDao.getMessagesByUser(null, user3, null, null,0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 3", 3, messages.size());
		messages = messageDao.getMessagesByUser(user2, null, TimeUtils.parseDateTime("2015-03-27 12:35:59"), null,0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 2", 2, messages.size());
		messages = messageDao.getMessagesByUser(user2, user3, null, TimeUtils.parseDateTime("2015-03-30 12:35:59"),0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 2", 2, messages.size());
		messages = messageDao.getMessagesByUser(user1, user3, TimeUtils.parseDateTime("2015-03-27 12:35:59"), TimeUtils.parseDateTime("2015-03-27 12:35:59"),0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 1", 1, messages.size());
		messages = messageDao.getMessagesByUser(user2, user3, TimeUtils.parseDateTime("2015-03-28 12:36:59"), TimeUtils.parseDateTime("2015-03-29 12:34:59"),0,0,SortDirection.Desc);
		Assert.assertEquals("messages' size should be 0", 0, messages.size());
		
		
		int size = messageDao.getMessagesCountByUser(user2, null, TimeUtils.parseDateTime("2015-03-27 12:35:59"), null);
		Assert.assertEquals("messages' size should be 2", 2, size);
		log.info("testGetMessagesByUser done!");
	}
}
