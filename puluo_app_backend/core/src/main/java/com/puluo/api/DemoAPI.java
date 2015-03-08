package com.puluo.api;

import com.puluo.api.result.ApiTestResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.util.Strs;

public class DemoAPI extends PuluoAPI<PuluoDSI,ApiTestResult> {

	public String msg;

	public DemoAPI(String msg) {
		this(msg,DaoApi.getInstance());
	}

	public DemoAPI(String msg, PuluoDSI dsi) {
		this.dsi = dsi;
		this.msg = msg;
	}

	@Override
	public void execute() {
		String user = dsi.userDao().getByMobile(msg).userUUID();
		rawResult = new ApiTestResult(Strs.join(user,":",msg));
	}
}
