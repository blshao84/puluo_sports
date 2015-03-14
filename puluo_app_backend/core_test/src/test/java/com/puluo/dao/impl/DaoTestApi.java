package com.puluo.dao.impl;

import com.puluo.dao.PgDummyDao;
import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.PuluoUserSettingDao;

public class DaoTestApi {
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
}
