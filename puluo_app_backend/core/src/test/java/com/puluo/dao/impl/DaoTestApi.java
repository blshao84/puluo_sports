package com.puluo.dao.impl;

import com.puluo.dao.PgDummyDao;

public class DaoTestApi {
	public static PgDummyDao pgDummy = 
			BeanTestFactory.getBean(PgDummyDao.class,"pgDummyDao");
}
