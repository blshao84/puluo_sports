package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoAuthCodeRecordDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoAuthCodeRecordDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoAuthCodeRecordDao authCodeDao = DaoTestApi.authCodeRecordDevDao;
		authCodeDao.createTable();
		authCodeDao.upsertRegistrationAuthCode("17721014665", "ABC123");
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.authCodeRecordDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testGetRegistrationAuthCodeFromMobile() {
		log.info("testGetRegistrationAuthCodeFromMobile start!");
		DaoTestApi.authCodeRecordDevDao.upsertRegistrationAuthCode("18521564305", "DEF456");
		PuluoAuthCodeRecord authCode = DaoTestApi.authCodeRecordDevDao.getRegistrationAuthCodeFromMobile("18521564305");
		Assert.assertEquals("auth code should be DEF456", "DEF456", authCode.authCode());
		log.info("testGetRegistrationAuthCodeFromMobile done!");
	}

	@Test 
	public void testUpsertRegistrationAuthCode() {
		log.info("testUpsertRegistrationAuthCode start!");
		PuluoAuthCodeRecordDao authCodeDao = DaoTestApi.authCodeRecordDevDao;
		authCodeDao.upsertRegistrationAuthCode("17721014665", "DEF456");
		PuluoAuthCodeRecord authCode = DaoTestApi.authCodeRecordDevDao.getRegistrationAuthCodeFromMobile("17721014665");
		Assert.assertEquals("auth code should be DEF456", "DEF456", authCode.authCode());
		authCodeDao.upsertRegistrationAuthCode("13262247972", "GHI789");
		authCode = DaoTestApi.authCodeRecordDevDao.getRegistrationAuthCodeFromMobile("13262247972");
		Assert.assertEquals("auth code should be DEF456", "GHI789", authCode.authCode());
		log.info("testUpsertRegistrationAuthCode done!");
	}
}
