package com.puluo.api.result;


import com.google.gson.Gson;

public class ApiTestResult {
	public String result;


	public ApiTestResult(String result) {
		super();
		this.result = result;
	}


	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
