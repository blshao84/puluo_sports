package com.puluo.dao.impl;

import com.puluo.dao.PuluoAuthCodeRecordDao;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.jdbc.DalTemplate;

public class PuluoAuthCodeRecordDaoImpl extends DalTemplate implements
		PuluoAuthCodeRecordDao {

	@Override
	public boolean upsertRegistrationAuthCode(String mobile, String authCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoAuthCodeRecord getRegistrationAuthCodeFromMobile(String mobile) {
		// TODO Auto-generated method stub
		return null;
	}

}
