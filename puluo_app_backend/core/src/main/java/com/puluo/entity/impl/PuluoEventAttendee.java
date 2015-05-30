package com.puluo.entity.impl;

import com.puluo.util.Strs;

public class PuluoEventAttendee {
	private final String name;
	private final String uuid;
	private final String thumbnail;

	public PuluoEventAttendee(String name, String uuid, String thumbnail) {
		super();
		this.name = name;
		this.uuid = uuid;
		this.thumbnail = thumbnail;
	}

	public String name() {
		if (Strs.isEmpty(name.trim()))
			return "匿名";
		else
			return name;
	}

	public String uuid() {
		return uuid;
	}

	public String thumbnail() {
		return thumbnail;
	}
}
