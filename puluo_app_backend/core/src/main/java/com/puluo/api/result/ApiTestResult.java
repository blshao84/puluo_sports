package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class ApiTestResult extends HasJSON {
	public String result;


	public ApiTestResult(String result) {
		super();
		this.result = result;
	}

}
