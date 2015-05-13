package com.puluo.test.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoAccountDao;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoAccount;
import com.puluo.entity.PuluoCoupon;
import com.puluo.entity.impl.PuluoAccountImpl;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoAccountNCouponDaoTest {
	
	public static Log log = LogFactory.getLog(PuluoAccountNCouponDaoTest.class);
	
	private static String mobile1 = "18521564305";
	private static String uuid1;
	private static String mobile2 = "17721014665";
	private static String uuid2;
	private static String mobile3 = "15338020432";
	private static String uuid3;
	private static List<String> accounts = new ArrayList<String>();
	private static List<String> coupons = new ArrayList<String>();

	@BeforeClass
	public static void setUpDB() {
		log.info("setUpDB start!");
		PuluoUserDao userDao = DaoTestApi.userDevDao;
		userDao.createTable();
		userDao.save(mobile1, mobile1);
		uuid1 = userDao.getByMobile(mobile1).userUUID();
		userDao.save(mobile2, mobile2);
		uuid2 = userDao.getByMobile(mobile2).userUUID();
		userDao.save(mobile3, mobile3);
		uuid3 = userDao.getByMobile(mobile3).userUUID();
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		couponDao.createTable();
		PuluoCoupon coupon1 = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Free, 0.0, uuid1, false);
		couponDao.insertCoupon(coupon1);
		coupons.add(coupon1.uuid());
		PuluoCoupon coupon2 = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Deduction, 100.0, uuid1, true);
		couponDao.insertCoupon(coupon2);
		coupons.add(coupon2.uuid());
		PuluoCoupon coupon3 = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Discount, 50.0, uuid1, true);
		couponDao.insertCoupon(coupon3);
		coupons.add(coupon3.uuid());
		PuluoCoupon coupon4 = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Deduction, 10.0, uuid2, false);
		couponDao.insertCoupon(coupon4);
		coupons.add(coupon4.uuid());
		PuluoCoupon coupon5 = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Free, 0.0, uuid2, true);
		couponDao.insertCoupon(coupon5);
		coupons.add(coupon5.uuid());
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		accountDao.createTable();
		PuluoAccount account1 = new PuluoAccountImpl(UUID.randomUUID().toString(), uuid1, 0.0, 0, new ArrayList<String>());
		accountDao.insertAccount(account1);
		accounts.add(account1.accountUUID());
		List<String> coupons_ = new ArrayList<String>();
		coupons.add(coupon4.uuid());
		PuluoAccount account2 = new PuluoAccountImpl(UUID.randomUUID().toString(), uuid2, 0.0, 0, coupons_);
		accountDao.insertAccount(account2);
		accounts.add(account2.accountUUID());
		log.info("setUpDB done!");
	}

	@AfterClass
	public static void cleanUpDB() {
		log.info("cleanUpDB start!");
		DalTemplate dao = (DalTemplate) DaoTestApi.userDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.accountDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		dao = (DalTemplate) DaoTestApi.couponDevDao;
		dao.getWriter().execute("drop table " + dao.getFullTableName());
		log.info("dropped table " + dao.getFullTableName());
		log.info("cleanUpDB done!");
	}

	@Test
	public void testGetByCouponUUID() {
		log.info("testGetByCouponUUID start!");
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		PuluoCoupon coupon = couponDao.getByCouponUUID(coupons.get(0));
		Assert.assertEquals("couponType应该为CouponType.Free!", CouponType.Free, coupon.couponType());
		Assert.assertEquals("amount应该为0.0!", new Double(0.0), coupon.amount());
		Assert.assertEquals("ownerUUID应该为" + uuid1 + "!", uuid1, coupon.ownerUUID());
		Assert.assertEquals("isValid应该为false!", false, coupon.isValid());
		log.info("testGetByCouponUUID done!");
	}

	@Test
	public void testGetByCouponUUIDnValid() {
		log.info("testGetByCouponUUIDnValid start!");
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		PuluoCoupon coupon = couponDao.getByCouponUUID(coupons.get(1), true);
		Assert.assertEquals("couponType应该为CouponType.Deduction!", CouponType.Deduction, coupon.couponType());
		Assert.assertEquals("amount应该为100.0!", new Double(100.0), coupon.amount());
		Assert.assertEquals("ownerUUID应该为" + uuid1 + "!", uuid1, coupon.ownerUUID());
		Assert.assertEquals("isValid应该为true!", true, coupon.isValid());
		coupon = couponDao.getByCouponUUID(coupons.get(0), true);
		Assert.assertEquals("coupon应该为null!", null, coupon);
		log.info("testGetByCouponUUIDnValid done!");
	}

	@Test
	public void testGetByUserUUID() {
		log.info("testGetByUserUUID start!");
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		List<PuluoCoupon> coupons = couponDao.getByUserUUID(uuid1);
		Assert.assertEquals("coupons.size()应该为3!", 3, coupons.size());
		log.info("testGetByUserUUID done!");
	}

	@Test
	public void testGetByUserUUIDnValid() {
		log.info("testGetByUserUUIDnValid start!");
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		List<PuluoCoupon> coupons = couponDao.getByUserUUID(uuid1, true);
		Assert.assertEquals("coupons.size()应该为2!", 2, coupons.size());
		log.info("testGetByUserUUIDnValid done!");
	}

	@Test
	public void testUpdateCoupon() {
		log.info("testUpdateCoupon start!");
		PuluoCouponDao couponDao = DaoTestApi.couponDevDao;
		PuluoCoupon coupon = new PuluoCouponImpl(coupons.get(4), CouponType.Discount, 15.0, uuid3, false);
		boolean result = couponDao.updateCoupon(coupon);
		Assert.assertTrue("updateCoupon应该返回true!", result);
		coupon = couponDao.getByCouponUUID(coupons.get(4));
		Assert.assertEquals("couponType应该为CouponType.Discount!", CouponType.Discount, coupon.couponType());
		Assert.assertEquals("amount应该为15.0!", new Double(15.0), coupon.amount());
		Assert.assertEquals("ownerUUID应该为" + uuid3 + "!", uuid3, coupon.ownerUUID());
		Assert.assertEquals("isValid应该为false!", false, coupon.isValid());
		coupon = new PuluoCouponImpl(UUID.randomUUID().toString(), CouponType.Free, 0.01, UUID.randomUUID().toString(), false);
		result = couponDao.updateCoupon(coupon);
		Assert.assertFalse("updateCoupon应该返回false!", result);
		log.info("testUpdateCoupon done!");
	}

	@Test
	public void testGetAccountByAccountUUID() {
		log.info("testGetByAccountUUID start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByAccountUUID(accounts.get(0));
		Assert.assertEquals("ownerUUID应该为" + uuid1 + "!", uuid1, account.ownerUUID());
		Assert.assertEquals("balance应该为0.0!", new Double(0.0), account.balance());
		Assert.assertEquals("credit应该为0!", new Integer(0), account.credit());
		log.info("testGetByAccountUUID done!");
	}

	@Test
	public void testGetAccountByUserUUID() {
		log.info("testGetAccountByUserUUID start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByUserUUID(uuid1);
		Assert.assertEquals("accountUUID应该为" + accounts.get(0) + "!", accounts.get(0), account.accountUUID());
		Assert.assertEquals("balance应该为0.0!", new Double(0.0), account.balance());
		Assert.assertEquals("credit应该为0!", new Integer(0), account.credit());
		log.info("testGetAccountByUserUUID done!");
	}

	@Test
	public void testUpdateBalance() {
		log.info("testUpdateBalance start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByAccountUUID(accounts.get(1));
		accountDao.updateBalance(account, 20.0);
		account = accountDao.getByAccountUUID(accounts.get(1));
		Assert.assertEquals("balance应该为20.0!", new Double(20.0), account.balance());
		account = new PuluoAccountImpl(UUID.randomUUID().toString(), uuid1, 0.0, 0, new ArrayList<String>());
		Assert.assertFalse("updateBalance应该返回false!", accountDao.updateBalance(account, 0.0));
		log.info("testUpdateBalance done!");
	}

	@Test
	public void testUpdateCredit() {
		log.info("testUpdateCredit start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByAccountUUID(accounts.get(1));
		accountDao.updateCredit(account, 20);
		account = accountDao.getByAccountUUID(accounts.get(1));
		Assert.assertEquals("credit应该为20!", new Integer(20), account.credit());
		account = new PuluoAccountImpl(UUID.randomUUID().toString(), uuid1, 0.0, 0, new ArrayList<String>());
		Assert.assertFalse("updateCredit应该返回false!", accountDao.updateCredit(account, 0));
		log.info("testUpdateCredit done!");
	}

	@Test
	public void testAddCoupon() {
		log.info("testAddCoupon start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByAccountUUID(accounts.get(0));
		accountDao.addCoupon(account, coupons.get(0));
		accountDao.addCoupon(account, coupons.get(1));
		accountDao.addCoupon(account, coupons.get(2));
		account = accountDao.getByAccountUUID(accounts.get(0));
		Assert.assertEquals("account.coupons().size()应该为3!", 3, ((PuluoAccountImpl)account).coupons(DaoTestApi.getInstance()).size());
		log.info("testAddCoupon done!");
	}

	@Test
	public void testRemoveCoupon() {
		log.info("testRemoveCoupon start!");
		PuluoAccountDao accountDao = DaoTestApi.accountDevDao;
		PuluoAccount account = accountDao.getByAccountUUID(accounts.get(1));
		accountDao.removeCoupon(account, coupons.get(3));
		account = accountDao.getByAccountUUID(accounts.get(1));
		Assert.assertEquals("account.coupons().size()应该为0!", 0, ((PuluoAccountImpl)account).coupons(DaoTestApi.getInstance()).size());
		log.info("testRemoveCoupon done!");
	}
}
