package com.puluo.dao;


public abstract  class PuluoDSI {
	public abstract  PuluoEventDao eventDao();
	public abstract  PuluoEventInfoDao eventInfoDao();
	public abstract  PuluoEventLocationDao eventLocationDao();
	public abstract  PuluoEventMemoryDao eventMemoryDao();
	public abstract  PuluoEventPosterDao eventPosterDao();
	public abstract  PuluoPaymentDao paymentDao();
	public abstract  PuluoTimelineCommentDao postCommentDao();
	public abstract  PuluoTimelineDao postDao();
	public abstract  PuluoTimelineLikeDao postLikeDao();
	public abstract  PuluoPrivateMessageDao privateMessageDao();
	public abstract  PuluoSessionDao sessionDao();
	public abstract  PuluoUserDao userDao();
	public abstract  PuluoUserFriendshipDao friendshipDao();
	public abstract  PuluoUserBlacklistDao blacklistDao();
	public abstract  PuluoOrderEventDao orderEventDao();
	public abstract  PuluoUserSettingDao userSettingDao();
	public abstract  PuluoAuthCodeRecordDao authCodeRecordDao();
	public abstract  PuluoWechatBindingDao wechatBindingDao();
	public abstract  PuluoFriendRequestDao friendRequestDao();
	public abstract  WechatMediaResourceDao wechatMediaResourceDao();
	public abstract  PuluoAccountDao accountDao();
	public abstract  PuluoCouponDao couponDao();
}
