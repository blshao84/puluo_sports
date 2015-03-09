package com.puluo.util;

import com.google.gson.GsonBuilder;

public abstract class HasJSON {
	public String toJson() {
		GsonBuilder gb = new GsonBuilder();
		gb.disableHtmlEscaping();
		return gb.create().toJson(this);
	}
}
