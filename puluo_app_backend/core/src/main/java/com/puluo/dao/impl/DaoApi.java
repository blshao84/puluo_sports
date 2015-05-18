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

public class DaoApi extends PuluoDSI {
	protected PuluoEventDao eventDao;
	protected PuluoEventInfoDao eventInfoDao;
	protected PuluoEventLocationDao eventLocationDao;
	protected PuluoEventMemoryDao eventMemoryDao;
	protected PuluoEventPosterDao eventPosterDao;
	protected PuluoPaymentDao paymentDao;
	protected PuluoTimelineCommentDao postCommentDao;
	protected PuluoTimelineLikeDao postLikeDao;
	protected PuluoTimelineDao postDao;
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
	protected PuluoRegistrationInvitationDao invitationDao;
	

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
		postCommentDao = BeanFactory.getBean(PuluoTimelineCommentDao.class,
				"postCommentDao");
		postLikeDao = BeanFactory
				.getBean(PuluoTimelineLikeDao.class, "postLikeDao");
		postDao = BeanFactory.getBean(PuluoTimelineDao.class, "postDao");
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
		accountDao = BeanFactory.getBean(PuluoAccountDao.class, "accountDao");
		couponDao = BeanFactory.getBean(PuluoCouponDao.class, "couponDao");
		invitationDao = BeanFactory.getBean(PuluoRegistrationInvitationDaoImpl.class, "invitationDao");
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
	public PuluoTimelineCommentDao postCommentDao() {

		return postCommentDao;
	}

	@Override
	public PuluoTimelineDao postDao() {

		return postDao;
	}

	@Override
	public PuluoTimelineLikeDao postLikeDao() {

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

	@Override
	public PuluoRegistrationInvitationDao invitationDao() {
		return invitationDao;
	}
}
