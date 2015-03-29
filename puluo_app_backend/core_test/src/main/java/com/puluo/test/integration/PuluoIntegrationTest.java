package com.puluo.test.integration;

import org.json.JSONObject;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PuluoIntegrationTest {

	public static void main(String[] args) {
		try {
			/*
			 * HttpResponse<JsonNode> jsonResponse =
			 * Unirest.get("http://puluodev1/ping").asJson();
			 * System.out.println(jsonResponse.getBody().toString());
			 * System.out.println(jsonResponse.getStatus());
			 */
			JsonNode body2 = new JsonNode(
					"{\"password\":\"uvwxyz\",\"mobile\":\"18521564305\"}");
			HttpResponse<JsonNode> jsonResponse2 = Unirest
					.post("http://localhost:8080/users/login")
					.header("Content-Type", "application/json").body(body2)
					.asJson();
			JsonNode json2 = jsonResponse2.getBody();
			System.out.println(json2.toString());
			System.out.println(jsonResponse2.getStatus());
			Headers headers2 = jsonResponse2.getHeaders();
			for (String k : headers2.keySet()) {
				System.out.println(k + ":" + headers2.get(k));
			}
			String session = getSessionID(json2);
			System.out.println(session);
			//
			JsonNode body3 = new JsonNode(String.format("{\"token\":\"%s\"}",session));
			HttpResponse<JsonNode> jsonResponse3 = Unirest
					.post("http://localhost:8080/users/18521564305")
					.header("Content-Type", "application/json").body(body3)
					.asJson();
			System.out.println(jsonResponse3.getBody().toString());

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getSessionID(JsonNode result) {
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
