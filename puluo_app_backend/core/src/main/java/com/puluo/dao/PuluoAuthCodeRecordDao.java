package com.puluo.dao;

import com.puluo.entity.PuluoAuthCodeRecord;

public interface PuluoAuthCodeRecordDao {
	
	public boolean saveRegistrationAuthCode(String mobile, String authCode);
	
	public PuluoAuthCodeRecord getRegistrationAuthCodeFromMobile(String mobile);
}
