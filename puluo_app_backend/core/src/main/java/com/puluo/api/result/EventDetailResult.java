package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;


public class EventDetailResult extends HasJSON{
	public String status;
	public String event_name;
	public String event_time;
	public String address;
	public String city;
	public String phone;
	public String coach_name;
	public String coach_uuid;
	public String thumbnail;
	public int registered_users;
	public int capacity;
	public int likes;
	public EventLocationResult geo_location;
	public String details;
	public List<String> images;
	
	public EventDetailResult(String status,
			String event_name, String event_time, String address,
			String city, String phone, String coach_name, String coach_uuid,
			String thumbnail, int registered_users, int capacity, int likes,
			EventLocationResult geo_location, String details, List<String> images) {
		super();
		this.status = status;
		this.event_name = event_name;
		this.event_time = event_time;
		this.address = address;
		this.city = city;
		this.phone = phone;
		this.coach_name = coach_name;
		this.coach_uuid = coach_uuid;
		this.thumbnail = thumbnail;
		this.registered_users = registered_users;
		this.capacity = capacity;
		this.likes = likes;
		this.geo_location = geo_location;
		this.details = details;
		this.images = images;
	}
	
	public static EventDetailResult dummy() {
		List<String> sample = new ArrayList<String>();
		sample.add("http://upyun.com/puluo/image1.jpg");
		sample.add("http://upyun.com/puluo/image2.jpg");
		
		return new EventDetailResult("open", "Weapons of Ass Reduction", 
				"2012-01-01 12:00:00", "888 Happy Mansion", "beijing", "86-555-5555",
				"Mr. Bob Smith", "de305d54-75b4-431b-adb2-eb6b9e546014", "http://upyun.com/puluo/head.jpg", 
				22, 30, 2,  new EventLocationResult("39.92889","116.38833"), "Get fit with friends.", sample);
	}
}

class EventLocationResult {
	public String latitude;
	public String longitude;

	public EventLocationResult(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
