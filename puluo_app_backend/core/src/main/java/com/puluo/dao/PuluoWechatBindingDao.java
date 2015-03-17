package com.puluo.dao;

import com.puluo.entity.PuluoWechatBinding;

public interface PuluoWechatBindingDao {
	
	public PuluoWechatBinding findByOpenId(String openId);
	
	public PuluoWechatBinding findByMobile(String mobile);
	
	public boolean saveBinding(String mobile, String openId, String authCode);
	
	public boolean updateBinding(String openId, boolean verified);
}
