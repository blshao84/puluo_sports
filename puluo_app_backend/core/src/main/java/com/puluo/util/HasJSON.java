package com.puluo.util;

import com.google.gson.Gson;

public abstract class HasJSON {
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
