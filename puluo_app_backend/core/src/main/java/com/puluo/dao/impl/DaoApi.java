package com.puluo.dao.impl;

import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventPhotoDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoReservationDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;

public class DaoApi {
	public static PuluoEventDao eventDao = BeanTestFactory.getBean(
			PuluoEventDao.class, "eventDao");
	public static PuluoEventLocationDao eventLocationDao = BeanFactory.getBean(
			PuluoEventLocationDao.class, "eventLocationDao");
	public static PuluoEventPhotoDao eventPhotoDao = BeanFactory.getBean(
			PuluoEventPhotoDao.class, "eventPhotoDao");
	public static PuluoPaymentDao paymentDao = BeanFactory.getBean(
			PuluoPaymentDao.class, "paymentDao");
	public static PuluoPostDao postDao = BeanFactory.getBean(
			PuluoPostDao.class, "postDao");
	public static PuluoPrivateMessageDao privateMessageDao = BeanFactory
			.getBean(PuluoPrivateMessageDao.class, "privateMessageDao");
	public static PuluoReservationDao reservationDao = BeanFactory.getBean(
			PuluoReservationDao.class, "reservationDao");
	public static PuluoSessionDao sessionDao = BeanFactory.getBean(
			PuluoSessionDao.class, "sessionDao");
	public static PuluoUserDao userDao = BeanFactory.getBean(
			PuluoUserDao.class, "userDao");
	public static PuluoUserFriendshipDao friendshipDao = BeanFactory.getBean(
			PuluoUserFriendshipDao.class, "friendshipDao");
}
