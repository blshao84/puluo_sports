package com.puluo.test.functional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoAuthCodeRecordDaoImpl;
import com.puluo.dao.impl.PuluoSessionDaoImpl;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.entity.PuluoUser;
import com.puluo.test.functional.util.APIFunctionalTest;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class RegistrationSMSFunctionalTest extends APIFunctionalTest {
	public static Log log = LogFactory
			.getLog(RegistrationSMSFunctionalTest.class);
	static String mobile1 = "123456789";
	static String authCode = "123456";
	static String nonExistingMobile = "11111111111";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		DaoApi.getInstance().userDao().save(mobile1, "abcdefg");
		DaoApi.getInstance().authCodeRecordDao().upsertRegistrationAuthCode(mobile1, authCode);
	}

	@AfterClass
	public static void cleanupDB() {
		PuluoUserDaoImpl dao = (PuluoUserDaoImpl) DaoApi.getInstance()
				.userDao();
		PuluoSessionDaoImpl sessionDao = (PuluoSessionDaoImpl) DaoApi
				.getInstance().sessionDao();
		PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl)DaoApi.getInstance().authCodeRecordDao();
		authDao.deleteByMobile(mobile1);
		PuluoUser user = dao.getByMobile(mobile1);
		if (user != null) {
			dao.deleteByUserUUID(user.userUUID());
			sessionDao.obliterateAllSessions(user.mobile());
		}
	}

	@Test
	public void testSuccessfulSend() {
		String inputs = String.format(
				"{\"mock\":\"true\",\"mobile\":\"%s\"}", nonExistingMobile);
		try {
			PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl)DaoApi.getInstance().authCodeRecordDao();
			authDao.deleteByMobile(nonExistingMobile);
			JsonNode json = callAPI("services/sms/register", inputs);
			String status = super.getStringFromJson(json, "status");
			Assert.assertEquals("successful send auth code should return status=success","success",status);
			PuluoAuthCodeRecord record = authDao.getRegistrationAuthCodeFromMobile(nonExistingMobile);
			Assert.assertNotNull(record);
			authDao.deleteByMobile(nonExistingMobile);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testSuccessfulSendRegistrationCodeWithExistingUser() {
		String inputs = String.format(
				"{\"mock\":\"true\",\"mobile\":\"%s\"}", mobile1);
		try {
			JsonNode json = callAPI("services/sms/register", inputs);
			String status = super.getStringFromJson(json, "status");
			Assert.assertEquals("successful send auth code should return status=success","success",status);
			PuluoAuthCodeRecord record = DaoApi.getInstance().authCodeRecordDao().getRegistrationAuthCodeFromMobile(mobile1);
			Assert.assertNotNull(record);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testSuccessfulSendResetCodeWithExistingUser() {
		String inputs = String.format(
				"{\"mock\":\"true\",\"mobile\":\"%s\"}", mobile1);
		try {
			JsonNode json = callAPI("services/sms/reset", inputs);
			String status = super.getStringFromJson(json, "status");
			Assert.assertEquals("successful send auth code should return status=success","success",status);
			PuluoAuthCodeRecord record = DaoApi.getInstance().authCodeRecordDao().getPasswordResetAuthCodeFromMobile(mobile1);
			Assert.assertNotNull(record);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testMissMobile() {
		try {
			PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl)DaoApi.getInstance().authCodeRecordDao();
			authDao.deleteByMobile(nonExistingMobile);
			JsonNode json = callAPI("services/sms/register", "");
			String error = super.getStringFromJson(json,"id");
			Assert.assertEquals("miss mobile should return error id 15","15", error);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void testFailedSend() {
		String inputs = String.format(
				"{\"mobile\":\"%s\"}", nonExistingMobile);
		try {
			PuluoAuthCodeRecordDaoImpl authDao = (PuluoAuthCodeRecordDaoImpl)DaoApi.getInstance().authCodeRecordDao();
			authDao.deleteByMobile(nonExistingMobile);
			JsonNode json = callAPI("services/sms/register", inputs);
			String error = super.getStringFromJson(json,"id");
			Assert.assertEquals("miss mobile should return error id 9","9", error);
			PuluoAuthCodeRecord record = authDao.getRegistrationAuthCodeFromMobile(nonExistingMobile);
			Assert.assertNull(record);
			authDao.deleteByMobile(nonExistingMobile);

		} catch (UnirestException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}
}
