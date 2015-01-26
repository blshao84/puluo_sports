package com.puluo.dao;

import com.puluo.dao.impl.BeanFactory;

public interface PuluoEventDao {
	public static PuluoUserDao userDao = 
			BeanFactory.getBean(PuluoUserDao.class,"userDao");
	public static PuluoEventDao eventDao = 
			BeanFactory.getBean(PuluoEventDao.class,"eventDao");
	public static PuluoEventLocationDao eventLocDao = 
			BeanFactory.getBean(PuluoEventLocationDao.class,"eventLocDao");
	public static PuluoPaymentDao paymentDao = 
			BeanFactory.getBean(PuluoPaymentDao.class,"paymentDao");
	public static PuluoPrivateMessageDao privateMsgDao = 
			BeanFactory.getBean(PuluoPrivateMessageDao.class,"privateMsgDao");
	public static PuluoPostDao postDao = 
			BeanFactory.getBean(PuluoPostDao.class,"postDao");
	public static PuluoReservationDao reservationDao = 
			BeanFactory.getBean(PuluoReservationDao.class,"reservationDao");
}
