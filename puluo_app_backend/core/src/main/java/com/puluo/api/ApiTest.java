package com.puluo.api;
import com.puluo.api.result.ApiTestResult;

public class ApiTest implements PuluoAPI {

	public String msg;
	
	public ApiTest(String msg) {
		this.msg = msg;
	}

	@Override
	public String result() {
		ApiTestResult r = new ApiTestResult(msg);
		return r.toJson();
	}
}
