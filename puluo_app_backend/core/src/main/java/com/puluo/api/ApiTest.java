package com.puluo.api;

import com.puluo.api.result.ApiTestResult;

public class ApiTest extends PuluoAPI<ApiTestResult> {

	public String msg;

	public ApiTest(String msg) {
		this.msg = msg;
	}

	@Override
	public ApiTestResult rawResult() {
		return new ApiTestResult(msg);
	}
}
