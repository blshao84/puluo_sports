package com.puluo.dao.impl;

import com.puluo.dao.PgDummyDao;
import com.puluo.dao.PuluoAccountDao;
import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.PuluoCouponDao;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoRegistrationInvitationDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.dao.PuluoTimelineDao;
import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.PuluoWechatBindingDao;
import com.puluo.dao.WechatMediaResourceDao;

public class DaoTestApi extends PuluoDSI {
	public static PgDummyDao pgInMemDummy = 
			BeanTestFactory.getBean(PgDummyDao.class,"pgDummyDao");
	
	public static PgDummyDao pgFileDummy = 
			BeanTestFactory.getBean(PgDummyDao.class,"pgDummyDao2");

	public static PuluoUserDao userDevDao	= BeanTestFactory.getBean(PuluoUserDaoImpl.class, "userDevDao");
	public static PuluoSessionDao sessionDevDao	= BeanTestFactory.getBean(PuluoSessionDaoImpl.class, "sessionDevDao");
	public static PuluoUserFriendshipDao friendDevDao	= BeanTestFactory.getBean(PuluoUserFriendshipDao.class, "friendDevDao");
	public static PuluoUserBlacklistDao blacklistDevDao	= BeanTestFactory.getBean(PuluoUserBlacklistDao.class, "blacklistDevDao");
	public static PuluoUserSettingDao settingDevDao	= BeanTestFactory.getBean(PuluoUserSettingDao.class, "settingDevDao");
	public static PuluoAuthCodeRecordDao authCodeRecordDevDao	= BeanTestFactory.getBean(PuluoAuthCodeRecordDao.class, "authCodeRecordDevDao");
	public static PuluoEventPosterDao eventPosterDevDao	= BeanTestFactory.getBean(PuluoEventPosterDao.class, "eventPosterDevDao");
	public static PuluoEventInfoDao eventInfoDevDao	= BeanTestFactory.getBean(PuluoEventInfoDao.class, "eventInfoDevDao");
	public static PuluoEventDao eventDevDao	= BeanTestFactory.getBean(PuluoEventDao.class, "eventDevDao");
	public static PuluoEventLocationDao eventLocationDevDao	= BeanTestFactory.getBean(PuluoEventLocationDao.class, "eventLocationDevDao");
	public static PuluoEventMemoryDao eventMemoryDevDao	= BeanTestFactory.getBean(PuluoEventMemoryDao.class, "eventMemoryDevDao");
	public static PuluoWechatBindingDao wechatBindingDevDao	= BeanTestFactory.getBean(PuluoWechatBindingDao.class, "wechatBindingDevDao");
	public static PuluoPrivateMessageDao privateMessageDevDao	= BeanTestFactory.getBean(PuluoPrivateMessageDao.class, "privateMessageDevDao");
	public static PuluoFriendRequestDao friendRequestDevDao	= BeanTestFactory.getBean(PuluoFriendRequestDao.class, "friendRequestDevDao");
	public static WechatMediaResourceDao wechatMediaResourceDao	= BeanTestFactory.getBean(WechatMediaResourceDaoImpl.class, "wechatMediaResourceDao");
	public static PuluoOrderEventDao orderEventDevDao = BeanTestFactory.getBean(PuluoOrderEventDao.class, "orderEventDevDao");
	public static PuluoPaymentDao paymentDevDao = BeanTestFactory.getBean(PuluoPaymentDao.class, "paymentDevDao");
	public static PuluoAccountDao accountDevDao = BeanTestFactory.getBean(PuluoAccountDao.class, "accountDevDao");
	public static PuluoCouponDao couponDevDao = BeanTestFactory.getBean(PuluoCouponDao.class, "couponDevDao");
	public static PuluoRegistrationInvitationDao invitationDao = BeanTestFactory.getBean(PuluoRegistrationInvitationDao.class, "invitationDao");
	public static PuluoTimelineDao postDevDao = BeanTestFactory.getBean(PuluoTimelineDao.class, "postDevDao");
	public static PuluoTimelineCommentDao postCommentDevDao = BeanTestFactory.getBean(PuluoTimelineCommentDao.class, "postCommentDevDao");
	public static PuluoTimelineLikeDao postLikeDevDao = BeanTestFactory.getBean(PuluoTimelineLikeDao.class, "postLikeDevDao");

	private static class SingletonHolder {
		private static final DaoTestApi INSTANCE = new DaoTestApi();
	}

	public static DaoTestApi getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public PuluoEventDao eventDao() {
		return eventDevDao;
	}

	@Override
	public PuluoEventPosterDao eventPosterDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPaymentDao paymentDao() {
		return paymentDevDao;
	}

	@Override
	public PuluoTimelineCommentDao postCommentDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoTimelineDao postDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoTimelineLikeDao postLikeDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPrivateMessageDao privateMessageDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoSessionDao sessionDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUserDao userDao() {
		return userDevDao;
	}

	@Override
	public PuluoUserFriendshipDao friendshipDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoOrderEventDao orderEventDao() {
		return orderEventDevDao;
	}

	@Override
	public PuluoUserSettingDao userSettingDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoAuthCodeRecordDao authCodeRecordDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoWechatBindingDao wechatBindingDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEventInfoDao eventInfoDao() {
		return eventInfoDevDao;
	}

	@Override
	public PuluoEventLocationDao eventLocationDao() {
		return eventLocationDevDao;
	}

	@Override
	public PuluoEventMemoryDao eventMemoryDao() {
		return eventMemoryDevDao;
	}

	@Override
	public PuluoFriendRequestDao friendRequestDao() {
		return friendRequestDevDao;
	}

	@Override
	public WechatMediaResourceDao wechatMediaResourceDao() {
		return wechatMediaResourceDao;
	}

	@Override
	public PuluoUserBlacklistDao blacklistDao() {
		return blacklistDevDao;
	}

	@Override
	public PuluoAccountDao accountDao() {
		return accountDevDao;
	}

	@Override
	public PuluoCouponDao couponDao() {
		return couponDevDao;
	}

	@Override
	public PuluoRegistrationInvitationDao invitationDao() {
		return invitationDao;
	}

}
