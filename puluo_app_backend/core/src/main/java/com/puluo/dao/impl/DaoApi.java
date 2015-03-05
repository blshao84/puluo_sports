package com.puluo.dao.impl;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoEventDao;
import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.PuluoEventLocationDao;
import com.puluo.dao.PuluoEventMemoryDao;
import com.puluo.dao.PuluoEventPosterDao;
import com.puluo.dao.PuluoOrderEventDao;
import com.puluo.dao.PuluoPaymentDao;
import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.PuluoPostLikeDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;

public class DaoApi extends PuluoDSI {
	protected  PuluoEventDao eventDao;
	protected  PuluoEventInfoDao eventInfoDao;
	protected  PuluoEventLocationDao eventLocationDao;
	protected  PuluoEventMemoryDao eventMemoryDao;
	protected  PuluoEventPosterDao eventPosterDao;
	protected  PuluoPaymentDao paymentDao;
	protected  PuluoPostCommentDao postCommentDao;
	protected  PuluoPostLikeDao postLikeDao;
	protected  PuluoPostDao postDao;
	protected  PuluoPrivateMessageDao privateMessageDao;
	protected  PuluoSessionDao sessionDao;
	protected  PuluoUserDao userDao;
	protected  PuluoUserFriendshipDao friendshipDao;
	protected  PuluoOrderEventDao orderEventDao;

	private DaoApi() {
		eventDao = BeanFactory.getBean(PuluoEventDao.class, "eventDao");
		eventInfoDao = BeanFactory.getBean(PuluoEventInfoDao.class,
				"eventInfoDao");
		eventLocationDao = BeanFactory.getBean(PuluoEventLocationDao.class,
				"eventLocationDao");
		eventMemoryDao = BeanFactory.getBean(PuluoEventMemoryDao.class,
				"eventMemoryDao");
		eventPosterDao = BeanFactory.getBean(PuluoEventPosterDao.class,
				"eventPosterDao");
		paymentDao = BeanFactory.getBean(PuluoPaymentDao.class, "paymentDao");
		postCommentDao = BeanFactory.getBean(PuluoPostCommentDao.class,
				"postCommentDao");
		postLikeDao = BeanFactory
				.getBean(PuluoPostLikeDao.class, "postLikeDao");
		postDao = BeanFactory.getBean(PuluoPostDao.class, "postDao");
		privateMessageDao = BeanFactory.getBean(PuluoPrivateMessageDao.class,
				"privateMessageDao");
		sessionDao = BeanFactory.getBean(PuluoSessionDao.class, "sessionDao");
		userDao = BeanFactory.getBean(PuluoUserDao.class, "userDao");
		friendshipDao = BeanFactory.getBean(PuluoUserFriendshipDao.class,
				"friendshipDao");
		orderEventDao = BeanFactory.getBean(PuluoOrderEventDao.class,
				"orderEventDao");
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		private static final DaoApi INSTANCE = new DaoApi();
	}

	public static DaoApi getInstance() {
		return SingletonHolder.INSTANCE;
	}

	@Override
	public PuluoEventDao eventDao() {
		// TODO Auto-generated method stub
		return eventDao;
	}

	@Override
	public PuluoEventInfoDao eventInfoDao() {
		// TODO Auto-generated method stub
		return eventInfoDao;
	}

	@Override
	public PuluoEventLocationDao eventLocationDao() {
		// TODO Auto-generated method stub
		return eventLocationDao;
	}

	@Override
	public PuluoEventMemoryDao eventMemoryDao() {
		// TODO Auto-generated method stub
		return eventMemoryDao;
	}

	@Override
	public PuluoEventPosterDao eventPosterDao() {
		// TODO Auto-generated method stub
		return eventPosterDao;
	}

	@Override
	public PuluoPaymentDao paymentDao() {
		// TODO Auto-generated method stub
		return paymentDao;
	}

	@Override
	public PuluoPostCommentDao postCommentDao() {
		// TODO Auto-generated method stub
		return postCommentDao;
	}

	@Override
	public PuluoPostDao postDao() {
		// TODO Auto-generated method stub
		return postDao;
	}

	@Override
	public PuluoPostLikeDao postLikeDao() {
		// TODO Auto-generated method stub
		return postLikeDao;
	}

	@Override
	public PuluoPrivateMessageDao privateMessageDao() {
		// TODO Auto-generated method stub
		return privateMessageDao;
	}

	@Override
	public PuluoSessionDao sessionDao() {
		// TODO Auto-generated method stub
		return sessionDao;
	}

	@Override
	public PuluoUserDao userDao() {
		// TODO Auto-generated method stub
		return userDao;
	}

	@Override
	public PuluoUserFriendshipDao friendshipDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoOrderEventDao orderEventDao() {
		// TODO Auto-generated method stub
		return orderEventDao;
	}
}
