package com.puluo.dao.impl;

import com.puluo.dao.PgDummyDao;
import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoFriendRequestDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.PuluoPostLikeDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.PuluoWechatBindingDao;

public class DaoTestApi extends PuluoDSI {
	public static PgDummyDao pgInMemDummy = 
			BeanTestFactory.getBean(PgDummyDao.class,"pgDummyDao");
	
	public static PgDummyDao pgFileDummy = 
			BeanTestFactory.getBean(PgDummyDao.class,"pgDummyDao2");

	public static PuluoUserDao userDevDao	= BeanTestFactory.getBean(PuluoUserDaoImpl.class, "userDevDao");
	public static PuluoSessionDao sessionDevDao	= BeanTestFactory.getBean(PuluoSessionDaoImpl.class, "sessionDevDao");
	public static PuluoUserFriendshipDao friendDevDao	= BeanTestFactory.getBean(PuluoUserFriendshipDao.class, "friendDevDao");
	public static PuluoUserSettingDao settingDevDao	= BeanTestFactory.getBean(PuluoUserSettingDao.class, "settingDevDao");
	public static PuluoAuthCodeRecordDao authCodeRecordDevDao	= BeanTestFactory.getBean(PuluoAuthCodeRecordDao.class, "authCodeRecordDevDao");
	public static PuluoEventPosterDao eventPosterDevDao	= BeanTestFactory.getBean(PuluoEventPosterDao.class, "eventPosterDevDao");
	public static PuluoEventInfoDao eventInfoDevDao	= BeanTestFactory.getBean(PuluoEventInfoDao.class, "eventInfoDevDao");
	public static PuluoEventDao eventDevDao	= BeanTestFactory.getBean(PuluoEventDao.class, "eventDevDao");
	public static PuluoEventLocationDao eventLocationDevDao	= BeanTestFactory.getBean(PuluoEventLocationDao.class, "eventLocationDevDao");
	public static PuluoEventMemoryDao eventMemoryDevDao	= BeanTestFactory.getBean(PuluoEventMemoryDao.class, "eventMemoryDevDao");
	public static PuluoWechatBindingDao wechatBindingDevDao	= BeanTestFactory.getBean(PuluoWechatBindingDao.class, "wechatBindingDevDao");
	public static PuluoPrivateMessageDao privateMessageDevDao	= BeanTestFactory.getBean(PuluoPrivateMessageDao.class, "privateMessageDevDao");
	
	private static class SingletonHolder {
		private static final DaoTestApi INSTANCE = new DaoTestApi();
	}

	public static DaoTestApi getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public PuluoEventDao eventDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEventPosterDao eventPosterDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPaymentDao paymentDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPostCommentDao postCommentDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPostDao postDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoPostLikeDao postLikeDao() {
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
