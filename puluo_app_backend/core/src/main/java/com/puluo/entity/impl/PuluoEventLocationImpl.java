package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventLocation;


public class PuluoEventLocationImpl implements PuluoEventLocation {

	private String idlocation;
	private String address;
	private String zip;
	private String name;
	private int court;
	private int capacity;
	private int type;
	
	
	public PuluoEventLocationImpl() {}
	
	public PuluoEventLocationImpl(String idlocation, String address,
			String zip, String name, int court, int capacity, int type) {
		this.idlocation = idlocation;
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
		return idlocation;
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

	protected String getIdlocation() {
		return idlocation;
	}

	public void setIdlocation(String idlocation) {
		this.idlocation = idlocation;
	}

	protected String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	protected String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	protected String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected int getCourt() {
		return court;
	}

	public void setCourt(int court) {
		this.court = court;
	}

	protected int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	protected int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
