package com.puluo.dao;

import com.puluo.entity.PuluoWechatBinding;

public interface PuluoWechatBindingDao {
	public boolean createTable();
	
	public PuluoWechatBinding findByOpenId(String openId);
	
	public PuluoWechatBinding findByMobile(String mobile);
	
	public boolean saveBinding(String mobile, String openId);
	
	public boolean updateBinding(String openId, int status);
	public boolean updateMobile(String openId, String mobile);
}
