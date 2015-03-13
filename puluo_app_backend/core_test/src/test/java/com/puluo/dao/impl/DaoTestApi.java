package com.puluo.dao.impl;

import com.puluo.dao.PgDummyDao;
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
}
