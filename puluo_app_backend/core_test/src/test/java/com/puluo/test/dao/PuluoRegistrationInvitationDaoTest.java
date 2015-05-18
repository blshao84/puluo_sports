package com.puluo.test.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puluo.dao.PuluoRegistrationInvitationDao;
import com.puluo.dao.impl.DaoTestApi;
import com.puluo.entity.PuluoRegistrationInvitation;
import com.puluo.entity.impl.PuluoRegistrationInvitationImpl;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class PuluoRegistrationInvitationDaoTest {
	public static Log log = LogFactory
			.getLog(PuluoRegistrationInvitationDaoTest.class);

	public static String uuid1 = "uuid1";
	public static String uuid2 = "uuid2";
	public static String uuid3 = "uuid3";
	public static String user1 = "tom";
	public static String user2 = "jerry";
	public static String user3 = "pete";

	@BeforeClass
	public static void setupDB() {
		cleanupDB();
		PuluoRegistrationInvitationDao dao = DaoTestApi.getInstance()
				.invitationDao();
		dao.createTable();
		dao.insertInvitation(new PuluoRegistrationInvitationImpl(uuid1, user1,
				user2, null, null, null));
		dao.insertInvitation(new PuluoRegistrationInvitationImpl(uuid2, user1,
				user3, null, null, null));
		dao.insertInvitation(new PuluoRegistrationInvitationImpl(uuid3, user2,
				user3, null, null, null));

	}

	@AfterClass
	public static void cleanupDB() {
		try {
			DalTemplate dao = (DalTemplate) DaoTestApi.getInstance()
					.invitationDao();
			dao.getWriter().execute("drop table " + dao.getFullTableName());
		} catch (Exception e) {
			log.warn(e.getMessage());
		}
	}

	@Test
	public void testGetByUUID() {
		PuluoRegistrationInvitationDao dao = DaoTestApi.getInstance()
				.invitationDao();
		PuluoRegistrationInvitation i1 = dao.getByUUID(uuid1);
		Assert.assertNotNull(i1);
		Assert.assertEquals(uuid1, i1.uuid());
		Assert.assertEquals(user1, i1.fromUUID());
		Assert.assertEquals(user2, i1.toUUID());
		Assert.assertEquals("",i1.couponUUID());
	}
	
	@Test
	public void testGetUserSentInvitations() {
		
		PuluoRegistrationInvitationDao dao = DaoTestApi.getInstance()
				.invitationDao();
		List<PuluoRegistrationInvitation> is = dao.getUserSentInvitations(user1);
		Assert.assertEquals(2, is.size());
		Set<String> actualUsers = new HashSet<String>();
		Set<String> expectedUsers = new HashSet<String>();
		expectedUsers.add(user2);
		expectedUsers.add(user3);
		for(PuluoRegistrationInvitation i:is){
			actualUsers.add(i.toUUID());
		}
		Assert.assertEquals(expectedUsers, actualUsers);
		
		is = dao.getUserSentInvitations(user3);
		Assert.assertEquals(0, is.size());		
	}
	
	@Test
	public void testGetUserReceivedInvitations() {
		
		PuluoRegistrationInvitationDao dao = DaoTestApi.getInstance()
				.invitationDao();
		List<PuluoRegistrationInvitation> is = dao.getUserReceivedInvitations(user3);
		Assert.assertEquals(2, is.size());
		Set<String> actualUsers = new HashSet<String>();
		Set<String> expectedUsers = new HashSet<String>();
		expectedUsers.add(user2);
		expectedUsers.add(user1);
		for(PuluoRegistrationInvitation i:is){
			actualUsers.add(i.fromUUID());
		}
		Assert.assertEquals(expectedUsers, actualUsers);
		
		is = dao.getUserReceivedInvitations(user1);
		Assert.assertEquals(0, is.size());		
	}
	
	@Test
	public void testUpdateInvitation() {
		String coupon_id = "coupon_uuid";
		PuluoRegistrationInvitationDao dao = DaoTestApi.getInstance()
				.invitationDao();
		dao.updateCoupon(uuid3, coupon_id);
		PuluoRegistrationInvitation i1 = dao.getByUUID(uuid3);
		Assert.assertEquals(coupon_id, i1.couponUUID());
		
	}
}
