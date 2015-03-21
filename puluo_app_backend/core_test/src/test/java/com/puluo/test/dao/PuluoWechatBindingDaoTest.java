package com.puluo.test.dao;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoWechatBindingDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoWechatBindingDaoTest {
	public static Log log = LogFactory.getLog(PuluoWechatBindingDaoTest.class);

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoWechatBindingDao bindingDao = DaoTestApi.wechatBindingDevDao;
		bindingDao.createTable();
		bindingDao.saveBinding("123456", "abcde");
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.wechatBindingDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testDefaultBindingStatus() {
		PuluoWechatBindingDao bindingDao = DaoTestApi.wechatBindingDevDao;
		PuluoWechatBinding binding = bindingDao.findByMobile("123456");
		Assert.assertNotNull("mobile=123456 should exist in DB", binding);
		Assert.assertTrue("default status should equal to 0",
				binding.status() == 0);
	}

	@Test
	public void testUpdateMobile() {
		PuluoWechatBindingDao bindingDao = DaoTestApi.wechatBindingDevDao;
		boolean success = bindingDao.updateMobile("abcde", "67890");
		Assert.assertTrue("update an existing openid should succeed", success);
		PuluoWechatBinding binding = bindingDao.findByOpenId("abcde");
		Assert.assertTrue("mobile should be updated to 67890", binding.mobile()
				.equals("67890"));
		success = bindingDao.updateMobile("abcdef", "67890");
		Assert.assertFalse("update non existing openid should fail", success);
	}
	
	@Test
	public void testUpdateStatus() {
		PuluoWechatBindingDao bindingDao = DaoTestApi.wechatBindingDevDao;
		boolean success = bindingDao.updateBinding("abcde",1);
		Assert.assertTrue("update an existing openid should succeed", success);
		PuluoWechatBinding binding = bindingDao.findByOpenId("abcde");
		Assert.assertTrue("status should be updated to 1", binding.status()==1);
		success = bindingDao.updateBinding("abcdef",1);
		Assert.assertFalse("update non existing openid should fail", success);
	}
}
