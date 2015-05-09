package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoAuthCodeRecord;
import com.puluo.entity.PuluoUser;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.UserPasswordUpdateResult;

public class UserPasswordUpdateAPI extends
		PuluoAPI<PuluoDSI, UserPasswordUpdateResult> {
	public String userUUID;
	public String authCode;
	public String new_password;

	public UserPasswordUpdateAPI(String userUUID, String password,
			String new_password) {
		this(userUUID, password, new_password, DaoApi.getInstance());
	}

	public UserPasswordUpdateAPI(String userUUID, String authCode,
			String new_password, PuluoDSI dsi) {
		this.dsi = dsi;
		this.userUUID = userUUID;
		this.authCode = authCode;
		this.new_password = new_password;
	}

	@Override
	public void execute() {
		PuluoUserDao userDao = dsi.userDao();
		PuluoUser user = userDao.getByUUID(userUUID);
		if (user == null) {
			this.error = ApiErrorResult.getError(18);
			return;
		} else {
			String mobile = user.mobile();
			PuluoAuthCodeRecord resetAuthCodeRecord = dsi.authCodeRecordDao()
					.getPasswordResetAuthCodeFromMobile(mobile);
			if (resetAuthCodeRecord != null) {
				if (resetAuthCodeRecord.authCode().equals(authCode)) {
					boolean success = userDao
							.updatePassword(user, new_password);
					if (success) {
						this.rawResult = new UserPasswordUpdateResult(authCode,
								new_password);
						return;
					} else {
						this.error = ApiErrorResult.getError(20);
						return;
					}
				} else {
					this.error = ApiErrorResult.getError(19);
					return;
				}
			} else {
				this.error = ApiErrorResult.getError(19);
				return;
			}
		}
	}

}
