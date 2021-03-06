package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.entity.PuluoUser;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.UserRegistrationResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserRegistrationAPI extends
		PuluoAPI<PuluoDSI, UserRegistrationResult> {
	public static Log log = LogFactory.getLog(UserRegistrationAPI.class);
	private final String mobile;
	private final String password;
	private final String auth_code;

	public UserRegistrationAPI(String mobile, String password, String auth_code) {
		this(mobile, password, auth_code, DaoApi.getInstance());
	}

	public UserRegistrationAPI(String mobile, String password,
			String auth_code, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.password = password;
		this.auth_code = auth_code;
	}

	@Override
	public void execute() {
		PuluoUser user = dsi.userDao().getByMobile(mobile);
		if (user != null) {
			this.error = ApiErrorResult.getError(5);
			return;
		} else {
			PuluoAuthCodeRecord authCodeRecord = dsi.authCodeRecordDao()
					.getRegistrationAuthCodeFromMobile(mobile);
			if (authCodeRecord == null) {
				this.error = ApiErrorResult.getError(6);
				return;
			} else {
				if (!authCodeRecord.authCode().equals(auth_code)) {
					log.error(String.format(
							"auth_code doesn't match: db=%s,api=%s",
							authCodeRecord.authCode(), auth_code));
					this.error = ApiErrorResult.getError(7);
					return;
				} else {
					PuluoUserDao userDao = dsi.userDao();
					userDao.save(mobile, password);
					PuluoUser newUser = userDao.getByMobile(mobile);
					boolean successSave2 = dsi.userSettingDao().saveNewSetting(
							newUser.userUUID());
					if (successSave2) {
						String uuid = dsi.userDao().getByMobile(mobile)
								.userUUID();
						this.rawResult = new UserRegistrationResult(uuid,
								mobile, password);
					} else {
						this.error = ApiErrorResult.getError(8);
						return;
					}
				}
			}
		}
	}

	public String userUUID() {
		if (rawResult == null)
			return null;
		else
			return rawResult.user_uuid;
	}

}
