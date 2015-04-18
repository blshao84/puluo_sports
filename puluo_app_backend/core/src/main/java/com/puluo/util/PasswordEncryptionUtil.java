package com.puluo.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class PasswordEncryptionUtil {
	public static Log log = LogFactory.getLog(PasswordEncryptionUtil.class);
	public static String host = "http://localhost:3333/encode";

	public static String encrypt(String text) {
		try {
			HttpResponse<String> request = Unirest.get(host)
					.queryString("key", text).asString();
			return request.getBody();
		} catch (UnirestException e) {
			return null;
		}
	}
}
