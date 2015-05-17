package com.puluo.api.service;

import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;

public abstract class WechatAPI {

	protected PuluoUser getUserFromOpenID(String openId) {
		PuluoWechatBinding binding = getBindingFromOpenId(openId);
		if (binding == null)
			return null;
		else {
			return binding.user();
		}
	}
	
	protected PuluoWechatBinding getBindingFromOpenId(String openId) {
		PuluoWechatBinding binding = DaoApi.getInstance().wechatBindingDao()
				.findByOpenId(openId);
		return binding;
	}
	
}
