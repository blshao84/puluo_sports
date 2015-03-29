package com.puluo.test.functional.util;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.util.Strs;

public abstract class APIFunctionalTest {
	protected String host = "http://localhost:8080/";
	protected JsonNode callAPI(String url, String inputJsonStr) throws UnirestException {
		JsonNode input = new JsonNode(inputJsonStr);
		String fullURL = Strs.join(host, url);
		HttpResponse<JsonNode> jsonResponse2 = Unirest.post(fullURL)
				.header("Content-Type", "application/json").body(input)
				.asJson();
		return jsonResponse2.getBody();
	}
	protected String getSessionID(JsonNode result) {
		JSONObject jo = result.getObject();
		if (jo != null) {
			String value = jo.getString("token");
			if (value == null)
				return "";
			else
				return value;
		} else
			return "";
	}
}
