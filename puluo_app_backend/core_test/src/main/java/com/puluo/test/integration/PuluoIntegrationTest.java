package com.puluo.test.integration;

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
					"{\"password\":\"8409bL01\",\"mobile\":\"18646655333\"}");
			HttpResponse<JsonNode> jsonResponse2 = Unirest
					.post("http://localhost:8080/users/login")
					.header("Content-Type", "application/json").body(body2)
					// .field("password", "8409bL01")
					// .field("mobile", "18646655333")
					.asJson();
			System.out.println(jsonResponse2.getBody().toString());
			System.out.println(jsonResponse2.getStatus());
			Headers headers2 = jsonResponse2.getHeaders();
			for (String k : headers2.keySet()) {
				System.out.println(k + ":" + headers2.get(k));
			}
			String session = getSessionID(headers2);
			System.out.println(session);
			
			HttpResponse<JsonNode> jsonResponse3 = Unirest
					.get("http://localhost:8080/users/18646655333")
					.header("JSESSIONID",session)
					.asJson();
			System.out.println(jsonResponse3.getBody().toString());

		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getSessionID(Headers headers) {
		if (headers.containsKey("set-cookie")) {
			String value = headers.getFirst("set-cookie");
			int first = value.indexOf('=');
			int last = value.indexOf(';');
			return value.substring(first+1, last);
		} else
			return "";
	}
}
