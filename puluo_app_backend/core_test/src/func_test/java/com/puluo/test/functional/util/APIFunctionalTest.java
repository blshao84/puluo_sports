package com.puluo.test.functional.util;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.util.Strs;

public abstract class APIFunctionalTest {
	protected String host = "http://localhost:8080/";

	protected JsonNode callAPI(String url, String inputJsonStr)
			throws UnirestException {
		JsonNode input = new JsonNode(inputJsonStr);
		String fullURL = Strs.join(host, url);
		HttpResponse<JsonNode> jsonResponse2 = Unirest.post(fullURL)
				.header("Content-Type", "application/json").body(input)
				.asJson();
		return jsonResponse2.getBody();
	}
	
	protected HttpResponse<String> callAPIWithRejectedResponse(String url, String inputJsonStr)
			throws UnirestException {
		JsonNode input = new JsonNode(inputJsonStr);
		String fullURL = Strs.join(host, url);
		return Unirest.post(fullURL)
				.header("Content-Type", "application/json").body(input).asString();
	}

	protected String getStringFromJson(JsonNode result, String key) {
		JSONObject jo = result.getObject();
		if (jo != null) {
			Object value = jo.get(key);
			if (value == null)
				return "";
			else
				return value.toString();
		} else
			return "";
	}

	protected String getSessionID(JsonNode result) {
		return getStringFromJson(result, "token");
	}
}
