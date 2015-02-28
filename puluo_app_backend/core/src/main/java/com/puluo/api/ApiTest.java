package com.puluo.api;

import com.puluo.api.result.ApiTestResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class ApiTest extends PuluoAPI<PuluoDSI,ApiTestResult> {

	public String msg;

	public ApiTest(String msg) {
		this(msg,new DaoApi());
	}

	public ApiTest(String msg, PuluoDSI dsi) {
		this.dsi = dsi;
		this.msg = msg;
	}

	@Override
	public void execute() {
		rawResult = new ApiTestResult(msg);
	}
}
