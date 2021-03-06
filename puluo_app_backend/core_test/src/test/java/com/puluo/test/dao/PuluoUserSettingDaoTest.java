package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoUserSetting;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoUserSettingDaoTest {
	public static Log log = LogFactory.getLog(PuluoUserSettingDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoUserSettingDao settingDao = DaoTestApi.settingDevDao;
		settingDao.createTable();
		settingDao.saveNewSetting("17721014665");
		settingDao.saveNewSetting("18521564305");
		settingDao.saveNewSetting("13262247972");
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.settingDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testSaveDupSetting() {
		log.info("testSaveDupSetting start!");
		boolean success = DaoTestApi.settingDevDao.saveNewSetting("17721014665");
		Assert.assertFalse("save a duplicate user should fail!", success);
		log.info("testSaveDupSetting done!");
	}

	@Test 
	public void testAutoFriend() {
		log.info("testAutoFriend start!");
		boolean success = DaoTestApi.settingDevDao.updateAutoFriend("17721014665", false);
		Assert.assertTrue("update auto_friend should be successful!", success);
		PuluoUserSetting setting = DaoTestApi.settingDevDao.getByUserUUID("17721014665");
		Assert.assertFalse("user's auto_friend should be false!", setting.autoAddFriend());
		log.info("testAutoFriend done!");
	}

	@Test
	public void testTimelineVisibility() {
		log.info("testTimelineVisibility start!");
		boolean success = DaoTestApi.settingDevDao.updateTimelineVisibility("17721014665", false);
		Assert.assertTrue("update timeline_public should be successful!", success);
		PuluoUserSetting setting = DaoTestApi.settingDevDao.getByUserUUID("17721014665");
		Assert.assertFalse("user's timeline_public should be false!", setting.isTimelinePublic());
		log.info("testTimelineVisibility done!");
	}
	
	@Test
	public void testSearchability() {
		log.info("testSearchability start!");
		boolean success = DaoTestApi.settingDevDao.updateSearchability("17721014665", false);
		Assert.assertTrue("update searchable should be successful!", success);
		PuluoUserSetting setting = DaoTestApi.settingDevDao.getByUserUUID("17721014665");
		Assert.assertFalse("user's searchable should be false!", setting.isSearchable());
		log.info("testSearchability done!");
	}

	
	@Test
	public void testGetByUserUUID() {
		log.info("testGetByUserUUID start!");
		PuluoUserSetting setting =  DaoTestApi.settingDevDao.getByUserUUID("15330820432");
		Assert.assertNull(setting);
		log.info("testGetByUserUUID done!");
	}
}
