package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserLoginResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;

public class UserLoginAPI extends PuluoAPI<PuluoDSI, UserLoginResult> {

	public String mobile;
	public String password;
	private PuluoSession session;

	public UserLoginAPI(String mobile, String password) {
		this(mobile, password, DaoApi.getInstance());
	}

	public UserLoginAPI(String mobile, String password, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.password = password;
	}

	public PuluoSession obtainSession() {
		return session;
	}

	@Override
	public void execute() {
		this.session = dsi.sessionDao().getByMobile(mobile);
	}

}
