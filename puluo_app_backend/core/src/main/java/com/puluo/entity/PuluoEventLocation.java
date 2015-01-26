package com.puluo.entity;

public interface PuluoEventLocation {

	String getAddress(String idlocation);
	
	String getZip(String idlocation);
	
	String getName(String idlocation);
	
	int getCourtNum(String idlocation);
	
	int getCapacity(String idlocation);
	
	int getType(String idlocation);
	
	String[] findLocationId(String address, String zip, String name, String capacity, String type);
}
