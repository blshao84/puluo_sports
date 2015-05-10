package com.puluo.test.functional;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserBlacklistDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.test.functional.util.PuluoAuthenticatedFunctionalTestRunner;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class BlacklistFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory.getLog(BlacklistFunctionalTest.class);
	static String mobile1 = "123456789";
	static String uuid1;
	static String mobile2 = "234567890";
	static String uuid2;
	static String mobile3 = "345678901";
	static String uuid3;

	static String password = "abcdefg";

	@BeforeClass
	public static void setupDB() {
		PuluoUserDaoImpl userDao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		userDao.save(mobile1, password);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		userDao.save(mobile2, password);
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		userDao.save(mobile3, password);
		uuid3 = userDao.getByMobile(mobile3).userUUID();
		PuluoUserBlacklistDaoImpl blacklistDaoImpl = (PuluoUserBlacklistDaoImpl) DaoApi
				.getInstance().blacklistDao();
		blacklistDaoImpl.banUser(uuid1, uuid2);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoUserBlacklistDaoImpl blacklistDaoImpl = (PuluoUserBlacklistDaoImpl) DaoApi
				.getInstance().blacklistDao();
		PuluoUser user;
		user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			blacklistDaoImpl.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile2);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			blacklistDaoImpl.deleteByUserUUID(user.userUUID());
		}
		user = dao.getByMobile(mobile3);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
			blacklistDaoImpl.deleteByUserUUID(user.userUUID());
		}
	}
	@Test
	public void testUpdateNonExistedUser(){
		super.runAuthenticatedTest(new BlacklistFunctionalTestRunner(mobile1) {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/blacklist/ban",
						inputs(session));
				String expected = "47";
				String actual = getStringFromJson(json, "id");
				Assert.assertEquals("should not allow update blacklist of nonexisted user and error id should be 47",expected, actual);
			}

			@Override
			public String inputs(String session) {
				// TODO Auto-generated method stub
				return String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session,"123456");
			}

		});
	}
	
	@Test
	public void testBanAndFree(){
		super.runAuthenticatedTest(new BlacklistFunctionalTestRunner(mobile2) {

			@Override
			public void run(String session) throws UnirestException {
				String input = 
						String.format("{\"token\":\"%s\"}", session);
				JsonNode json = callAPI("users/blacklist/get/"+uuid2,input);
				List<JsonNode> res = getJsonArrayFromJson(json,"details");
				Assert.assertEquals(0, res.size());
				input = String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session,uuid3);
				json = callAPI("users/blacklist/ban",input);
				res = getJsonArrayFromJson(json,"details");
				Assert.assertEquals(1, res.size());
				input = String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session,uuid3);
				json = callAPI("users/blacklist/free",input);
				res = getJsonArrayFromJson(json,"details");
				Assert.assertEquals(0, res.size());
				
			}

			@Override
			public String inputs(String session) {
				// TODO Auto-generated method stub
				return null;
			}

		});
	}
	
	@Test
	public void testBanOneself(){
		super.runAuthenticatedTest(new BlacklistFunctionalTestRunner(mobile1) {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/blacklist/ban",
						inputs(session));
				String expected = "50";
				String actual = getStringFromJson(json, "id");
				Assert.assertEquals("should not allow a user to ban himself and error id should be 50",expected, actual);
			}

			@Override
			public String inputs(String session) {
				// TODO Auto-generated method stub
				return String.format("{\"token\":\"%s\",\"user_uuid\":\"%s\"}", session,uuid1);
			}

		});
	}

	@Test
	public void testListBlacklistWithMobile() {
		super.runAuthenticatedTest(new BlacklistFunctionalTestRunner(mobile1) {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/blacklist/get/" + mobile1,
						inputs(session));
				ArrayList<JsonNode> res = getJsonArrayFromJson(json, "details");
				Assert.assertEquals(1, res.size());
				String expected = uuid2;
				String actual = getStringFromJson(res.get(0), "uuid");
				Assert.assertEquals(expected, actual);

				json = callAPI("users/blacklist/get/" + mobile2, inputs(session));
				res = getJsonArrayFromJson(json, "details");
				Assert.assertEquals(0, res.size());
			}

			@Override
			public String inputs(String session) {
				// TODO Auto-generated method stub
				return String.format("{\"token\":\"%s\"}", session);
			}

		});
	}
	
	@Test
	public void testListBlacklistWithUUID() {
		super.runAuthenticatedTest(new BlacklistFunctionalTestRunner(mobile1) {

			@Override
			public void run(String session) throws UnirestException {
				JsonNode json = callAPI("users/blacklist/get/" + uuid1,
						inputs(session));
				ArrayList<JsonNode> res = getJsonArrayFromJson(json, "details");
				Assert.assertEquals(1, res.size());
				String expected = uuid2;
				String actual = getStringFromJson(res.get(0), "uuid");
				Assert.assertEquals(expected, actual);

				json = callAPI("users/blacklist/get/" + uuid2, inputs(session));
				res = getJsonArrayFromJson(json, "details");
				Assert.assertEquals(0, res.size());
			}

			@Override
			public String inputs(String session) {
				// TODO Auto-generated method stub
				return String.format("{\"token\":\"%s\"}", session);
			}

		});
	}

	abstract class BlacklistFunctionalTestRunner implements
			PuluoAuthenticatedFunctionalTestRunner {
		String mobile;
		
		public BlacklistFunctionalTestRunner(String mobile){
			this.mobile = mobile;
		}
		@Override
		public String password() {
			// TODO Auto-generated method stub
			return password;
		}

		@Override
		public String mobile() {
			// TODO Auto-generated method stub
			return mobile;
		}
	}
}
