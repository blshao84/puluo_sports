package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventLocation;


public class PuluoEventLocationImpl implements PuluoEventLocation {

	private String location_id;
	private String address;
	private String zip;
	private String name;
	private String phone;
	private String city;
	private double longitude;
	private double latitude;
	private int court;
	private int capacity;
	private int type;

	public PuluoEventLocationImpl(String location_id, String address,
			String zip, String name, String phone, String city,
			double longitude, double latitude, int court, int capacity,
			int type) {
		super();
		this.location_id = location_id;
		this.address = address;
		this.zip = zip;
		this.name = name;
		this.phone = phone;
		this.city = city;
		this.longitude = longitude;
		this.latitude = latitude;
		this.court = court;
		this.capacity = capacity;
		this.type = type;
	}

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
	public double longitude() {
		return longitude;
	}
	
	@Override
	public double latitude() {
		return latitude;
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
