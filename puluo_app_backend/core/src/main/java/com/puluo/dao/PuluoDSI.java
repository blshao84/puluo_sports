package com.puluo.dao;


public abstract  class PuluoDSI {
	public abstract  PuluoEventDao eventDao();
	public abstract  PuluoEventInfoDao eventInfoDao();
	public abstract  PuluoEventLocationDao eventLocationDao();
	public abstract  PuluoEventMemoryDao eventMemoryDao();
	public abstract  PuluoEventPosterDao eventPosterDao();
	public abstract  PuluoPaymentDao paymentDao();
	public abstract  PuluoPostCommentDao postCommentDao();
	public abstract  PuluoPostDao postDao();
	public abstract  PuluoPostLikeDao postLikeDao();
	public abstract  PuluoPrivateMessageDao privateMessageDao();
	public abstract  PuluoSessionDao sessionDao();
	public abstract  PuluoUserDao userDao();
	public abstract  PuluoUserFriendshipDao friendshipDao();
	public abstract  PuluoOrderEventDao orderEventDao();
	public abstract  PuluoUserSettingDao userSettingDao();
	public abstract  PuluoAuthCodeRecordDao authCodeRecordDao();
	public abstract  PuluoWechatBindingDao wechatBindingDao();
}
