package com.puluo.dao.impl;

import com.puluo.dao.PuluoWechatBindingDao;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.jdbc.DalTemplate;

public class PuluoWechatBindingDaoImpl extends DalTemplate implements PuluoWechatBindingDao {

	@Override
	public PuluoWechatBinding findByOpenId(String openId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoWechatBinding findByMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveBinding(String mobile, String openId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateBinding(String openId, int status) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMobile(String openId, String mobile) {
		// TODO Auto-generated method stub
		return false;
	}


}
