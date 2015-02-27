package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventLocation;

public class PuluoEventLocationImpl implements PuluoEventLocation {

	private String location_id;
	private String address;
	private String zip;
	private String name;
	private int court;
	private int capacity;
	private int type;

	public PuluoEventLocationImpl() {
	}

	public PuluoEventLocationImpl(String idlocation, String address,
			String zip, String name, int court, int capacity, int type) {
		this.location_id = idlocation;
		this.address = address;
		this.zip = zip;
		this.name = name;
		this.court = court;
		this.capacity = capacity;
		this.type = type;
	}

	@Override
	public String idLocation() {
		// TODO Auto-generated method stub
		return location_id;
	}

	@Override
	public String address() {
		// TODO Auto-generated method stub
		return address;
	}

	@Override
	public String zip() {
		// TODO Auto-generated method stub
		return zip;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int court() {
		// TODO Auto-generated method stub
		return court;
	}

	@Override
	public int capacity() {
		// TODO Auto-generated method stub
		return capacity;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return type;
	}

}
