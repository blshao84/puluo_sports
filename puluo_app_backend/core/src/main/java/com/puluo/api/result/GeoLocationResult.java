package com.puluo.api.result;

import java.util.List;

public class GeoLocationResult {
	public final String state;
	public final List<String> cities;
	public GeoLocationResult(String state, List<String> cities) {
		super();
		this.state = state;
		this.cities = cities;
	}
	
	
}
