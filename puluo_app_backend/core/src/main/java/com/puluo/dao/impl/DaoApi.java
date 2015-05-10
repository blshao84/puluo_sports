package com.puluo.dao.impl;

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
import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.dao.PuluoPostDao;
import com.puluo.dao.PuluoPostLikeDao;
import com.puluo.dao.PuluoPrivateMessageDao;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserFriendshipDao;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.PuluoWechatBindingDao;
import com.puluo.dao.WechatMediaResourceDao;

public class DaoApi extends PuluoDSI {
	protected PuluoEventDao eventDao;
	protected PuluoEventInfoDao eventInfoDao;
	protected PuluoEventLocationDao eventLocationDao;
	protected PuluoEventMemoryDao eventMemoryDao;
	protected PuluoEventPosterDao eventPosterDao;
	protected PuluoPaymentDao paymentDao;
	protected PuluoPostCommentDao postCommentDao;
	protected PuluoPostLikeDao postLikeDao;
	protected PuluoPostDao postDao;
	protected PuluoPrivateMessageDao privateMessageDao;
	protected PuluoSessionDao sessionDao;
	protected PuluoUserDao userDao;
	protected PuluoUserFriendshipDao friendshipDao;
	protected PuluoUserBlacklistDao blacklistDao;
	protected PuluoOrderEventDao orderEventDao;
	protected PuluoUserSettingDao userSettingDao;
	protected PuluoAuthCodeRecordDao authCodeRecordDao;
	protected PuluoWechatBindingDao wechatBindingDao;
	protected PuluoFriendRequestDao friendRequestDao;
	protected WechatMediaResourceDao wechatMediaResourceDao;
	protected PuluoAccountDao accountDao;
	protected PuluoCouponDao couponDao;
	

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
		blacklistDao = BeanFactory.getBean(PuluoUserBlacklistDao.class,
				"blacklistDao");
		orderEventDao = BeanFactory.getBean(PuluoOrderEventDao.class,
				"orderEventDao");
		userSettingDao = BeanFactory.getBean(PuluoUserSettingDao.class,
				"userSettingDao");
		authCodeRecordDao = BeanFactory.getBean(PuluoAuthCodeRecordDao.class,
				"authCodeRecordDao");
		wechatBindingDao = BeanFactory.getBean(PuluoWechatBindingDaoImpl.class,
				"wechatBindingDao");
		friendRequestDao = BeanFactory.getBean(PuluoFriendRequestDaoImpl.class,
				"friendRequestDao");
		wechatMediaResourceDao = BeanFactory.getBean(
				WechatMediaResourceDaoImpl.class, "wechatMediaResourceDao");
		//accountDao = BeanFactory.getBean(PuluoAccountDao.class, "accountDao");
		//couponDao = BeanFactory.getBean(PuluoCouponDao.class, "couponDao");
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

		return eventDao;
	}

	@Override
	public PuluoEventInfoDao eventInfoDao() {

		return eventInfoDao;
	}

	@Override
	public PuluoEventLocationDao eventLocationDao() {

		return eventLocationDao;
	}

	@Override
	public PuluoEventMemoryDao eventMemoryDao() {

		return eventMemoryDao;
	}

	@Override
	public PuluoEventPosterDao eventPosterDao() {

		return eventPosterDao;
	}

	@Override
	public PuluoPaymentDao paymentDao() {

		return paymentDao;
	}

	@Override
	public PuluoPostCommentDao postCommentDao() {

		return postCommentDao;
	}

	@Override
	public PuluoPostDao postDao() {

		return postDao;
	}

	@Override
	public PuluoPostLikeDao postLikeDao() {

		return postLikeDao;
	}

	@Override
	public PuluoPrivateMessageDao privateMessageDao() {

		return privateMessageDao;
	}

	@Override
	public PuluoSessionDao sessionDao() {

		return sessionDao;
	}

	@Override
	public PuluoUserDao userDao() {

		return userDao;
	}

	@Override
	public PuluoUserFriendshipDao friendshipDao() {

		return friendshipDao;
	}

	@Override
	public PuluoOrderEventDao orderEventDao() {

		return orderEventDao;
	}

	@Override
	public PuluoUserSettingDao userSettingDao() {

		return userSettingDao;
	}

	@Override
	public PuluoAuthCodeRecordDao authCodeRecordDao() {
		return authCodeRecordDao;
	}

	@Override
	public PuluoWechatBindingDao wechatBindingDao() {
		return wechatBindingDao;
	}

	@Override
	public PuluoFriendRequestDao friendRequestDao() {
		return friendRequestDao;
	}

	@Override
	public WechatMediaResourceDao wechatMediaResourceDao() {
		return wechatMediaResourceDao;
	}

	@Override
	public PuluoUserBlacklistDao blacklistDao() {
		return blacklistDao;
	}

	@Override
	public PuluoAccountDao accountDao() {
		return accountDao;
	}

	@Override
	public PuluoCouponDao couponDao() {
		return couponDao;
	}
}
