package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.entity.impl.PuluoEventAttendee;
import com.puluo.util.HasJSON;


public class EventDetailResult extends HasJSON{
	public String status;
	public String event_name;
	public long event_time;
	public String address;
	public String city;
	public String phone;
	public String coach_name;
	public String coach_uuid;
	public List<String> thumbnail;
	public int registered_users;
	public int capacity;
	public int likes;
	public EventLocationResult geo_location;
	public String details;
	public List<String> images;
	public double price;
	public List<PuluoEventAttendee> attendees;
	public boolean registered;
	
	public EventDetailResult(String status,
			String event_name, long event_time, String address,
			String city, String phone, String coach_name, String coach_uuid,
			List<String> thumbnail, int registered_users, int capacity, int likes,
			EventLocationResult geo_location, String details, List<String> images, double price, List<PuluoEventAttendee> attendees, boolean registered) {
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
		this.price = price;
		this.attendees = attendees;
		this.registered = registered;
	}
	
	public EventDetailResult(String status,
			String event_name, long event_time, String address,
			String city, String phone, String coach_name, String coach_uuid,
			List<String> thumbnail, int registered_users, int capacity, int likes,
			double latitude, double longitude, String details, List<String> images, double price, List<PuluoEventAttendee> attendees, boolean registered) {
		this(status, event_name, event_time, address, city, phone, coach_name, coach_uuid, thumbnail, registered_users,
				capacity, likes, new EventLocationResult(latitude, longitude), details, images, price, attendees, registered);
	}
	
	public static EventDetailResult dummy() {
		List<String> thumbnails = new ArrayList<String>();
		thumbnails.add("http://upyun.com/puluo/head.jpg");

		List<String> images = new ArrayList<String>();
		images.add("http://upyun.com/puluo/image1.jpg");
		images.add("http://upyun.com/puluo/image2.jpg");
		

		List<PuluoEventAttendee> attendees = new ArrayList<PuluoEventAttendee>();
		attendees.add(new PuluoEventAttendee("Lei", "0", "http://upyun.com/puluo/thumbnail0.jpg"));
		attendees.add(new PuluoEventAttendee("Baolin", "1", "http://upyun.com/puluo/thumbnail1.jpg"));
		
		return new EventDetailResult("open", "Weapons of Ass Reduction", 
				1427007059034L, "888 Happy Mansion", "beijing", "86-555-5555",
				"Mr. Bob Smith", "de305d54-75b4-431b-adb2-eb6b9e546014", thumbnails, 
				22, 30, 2,  new EventLocationResult(39.92889, 116.38833), "Get fit with friends.", images, 0.0, attendees, false);
	}
}

class EventLocationResult {
	public double latitude;
	public double longitude;

	public EventLocationResult(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
}
