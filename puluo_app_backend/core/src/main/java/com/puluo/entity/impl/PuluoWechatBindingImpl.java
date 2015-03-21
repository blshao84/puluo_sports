package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;

public class PuluoWechatBindingImpl implements PuluoWechatBinding {
	private final String user_mobile;
	private final String open_id;
	/**
	 * 0: binding requested, not sent sms 1: sms sent 2: verified
	 */
	private final int status;
	private final DateTime created_at;
	
	

	public PuluoWechatBindingImpl(String user_mobile, String open_id,
			int status, DateTime created_at) {
		super();
		this.user_mobile = user_mobile;
		this.open_id = open_id;
		this.status = status;
		this.created_at = created_at;
	}

	@Override
	public PuluoUser user() {
		return DaoApi.getInstance().userDao().getByMobile(user_mobile);
	}

	public PuluoUser user(PuluoDSI dsi) {
		return dsi.userDao().getByMobile(user_mobile);
	}

	@Override
	public String mobile() {
		return user_mobile;
	}

	@Override
	public String openId() {
		return open_id;
	}

	@Override
	public boolean verified() {
		return status == 2;
	}

	@Override
	public int status() {
		return status;
	}

}
