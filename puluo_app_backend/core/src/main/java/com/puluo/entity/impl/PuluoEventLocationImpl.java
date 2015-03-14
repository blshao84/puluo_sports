package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventLocation;


public class PuluoEventLocationImpl implements PuluoEventLocation {

	private String location_id;
	private String address;
	private String zip;
	private String name;
	private String phone;
	private String city;
	private String longitude;
	private String lattitude;
	private int court;
	private int capacity;
	private int type;

	
	@Override
	public String locationId() {
		return location_id;
	}

	@Override
	public String address() {
		return address;
	}

	@Override
	public String zip() {
		return zip;
	}

	@Override
	public String name() {
		return name;
	}
	
	@Override
	public String phone() {
		return phone;
	}
	
	@Override
	public String city() {
		return city;
	}
	
	@Override
	public String longitude() {
		return longitude;
	}
	
	@Override
	public String lattitude() {
		return lattitude;
	}

	@Override
	public int court() {
		return court;
	}

	@Override
	public int capacity() {
		return capacity;
	}

	@Override
	public int type() {
		return type;
	}
}
