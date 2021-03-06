package com.puluo.test.functional.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

public abstract class APIFunctionalTest {
	public static Log log = LogFactory.getLog(APIFunctionalTest.class);

	protected String host = "http://localhost:8080/";

	protected void runAuthenticatedTest(
			PuluoAuthenticatedFunctionalTestRunner runner) {
		String session = login(runner.mobile(), runner.password());
		if (session != null) {
			try {
				runner.run(session);
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("fail to run api");
			}
		} else
			Assert.fail("fail to login");
	}

	protected JsonNode callAPI(String url, String inputJsonStr)
			throws UnirestException {
		JsonNode input = new JsonNode(inputJsonStr);
		String fullURL = Strs.join(host, url);
		HttpResponse<JsonNode> jsonResponse2 = Unirest.post(fullURL)
				.header("Content-Type", "application/json").body(input)
				.asJson();
		return jsonResponse2.getBody();
	}

	protected HttpResponse<String> callAPIWithRejectedResponse(String url,
			String inputJsonStr) throws UnirestException {
		JsonNode input = new JsonNode(inputJsonStr);
		String fullURL = Strs.join(host, url);
		return Unirest.post(fullURL).header("Content-Type", "application/json")
				.body(input).asString();
	}

	protected String getStringFromJson(JsonNode result, String key) {
		JSONObject jo = result.getObject();
		return extractStringFromJSON(jo, key);
	}

	protected String getStringFromJson(JsonNode result, String key1, String key2) {
		JSONObject jo = result.getObject();
		if (jo != null) {
			JSONObject value = jo.getJSONObject(key1);
			return extractStringFromJSON(value, key2);
		} else
			return "";
	}

	protected String getStringFromJson(JsonNode result, String key1,
			String key2, String key3) {
		JSONObject jo = result.getObject();
		if (jo != null) {
			JSONObject value = jo.getJSONObject(key1);
			if (value != null) {
				JSONObject value2 = value.getJSONObject(key2);
				return extractStringFromJSON(value2, key3);
			} else
				return "";
		} else
			return "";
	}

	protected ArrayList<String> getStringArrayFromJson(JsonNode result,
			String key) {
		JSONObject jo = result.getObject();
		ArrayList<String> array = new ArrayList<String>();
		if (jo != null) {
			JSONArray value = jo.getJSONArray(key);
			if (value != null) {
				for (int i = 0; i < value.length(); i++) {
					array.add(value.getString(i));
				}
			}

		}
		return array;
	}

	protected ArrayList<JsonNode> getJsonArrayFromJson(JsonNode result,
			String key) {
		JSONObject jo = result.getObject();
		ArrayList<JsonNode> array = new ArrayList<JsonNode>();
		if (jo != null) {
			JSONArray value = jo.getJSONArray(key);
			if (value != null) {
				for (int i = 0; i < value.length(); i++) {
					array.add(new JsonNode(String.valueOf(value.get(i))));
				}
			}

		}
		return array;
	}

	protected String getSessionID(JsonNode result) {
		return getStringFromJson(result, "token");
	}

	protected String login(String mobile, String password) {
		String inputs = String.format(
				"{\"password\":\"%s\",\"mobile\":\"%s\"}", password, mobile);
		try {
			JsonNode json2 = callAPI("users/login", inputs);
			String session = getSessionID(json2);
			log.info("successfully aquired session:" + session);
			return session;
		} catch (UnirestException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected String extractStringFromJSON(JSONObject jo, String key) {
		if (jo != null) {
			Object value = jo.get(key);
			if (value == null)
				return "";
			else
				return value.toString();
		} else
			return "";
	}
}
