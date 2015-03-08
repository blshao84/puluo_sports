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

	@Override
	public String lattitude() {
		// TODO Auto-generated method stub
		return lattitude;
	}

	@Override
	public String longitude() {
		// TODO Auto-generated method stub
		return longitude;
	}

	@Override
	public String city() {
		// TODO Auto-generated method stub
		return city;
	}

	@Override
	public String phone() {
		// TODO Auto-generated method stub
		return phone;
	}

}
