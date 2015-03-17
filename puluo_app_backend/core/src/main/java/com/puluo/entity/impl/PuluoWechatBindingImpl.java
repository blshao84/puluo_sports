package com.puluo.entity.impl;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;

public class PuluoWechatBindingImpl implements PuluoWechatBinding {
	private String user_mobile;
	private String open_id;
	private String auth_code;
	private boolean verified;

	@Override
	public PuluoUser user() {
		return DaoApi.getInstance().userDao().getByMobile(user_mobile);
	}

	public PuluoUser user(PuluoDSI dsi) {
		return dsi.userDao().getByMobile(user_mobile);
	}

	@Override
	public String mobile() {
		return user().mobile();
	}

	@Override
	public String openId() {
		return open_id;
	}

	@Override
	public boolean verified() {
		return verified;
	}

	@Override
	public String authCode() {
		return auth_code;
	}

}
