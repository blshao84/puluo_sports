package com.puluo.dao;

import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.enumeration.PuluoAuthCodeType;

public interface PuluoAuthCodeRecordDao {
	
	public boolean upsertRegistrationAuthCode(String mobile, String authCode);
	
	public PuluoAuthCodeRecord getRegistrationAuthCodeFromMobile(String mobile);

	boolean createTable();

	boolean upsertPasswordResetAuthCode(String mobile, String authCode);

	boolean upsertAuthCode(String mobile, String authCode,PuluoAuthCodeType authType);

	PuluoAuthCodeRecord getAuthCodeFromMobile(String mobile,
			PuluoAuthCodeType authType);

	PuluoAuthCodeRecord getPasswordResetAuthCodeFromMobile(String mobile);
}
