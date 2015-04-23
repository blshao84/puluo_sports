package com.puluo.entity.impl;

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
		return name;
	}
	
	public String uuid() {
		return uuid;
	}
	
	public String thumbnail() {
		return thumbnail;
	}
}
