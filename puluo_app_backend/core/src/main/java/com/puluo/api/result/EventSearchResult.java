package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.entity.impl.PuluoEventImpl;
import com.puluo.util.HasJSON;
import com.puluo.util.TimeUtils;

public class EventSearchResult extends HasJSON {
	public List<EventSearchResultDetail> events;

	public EventSearchResult() {
		super();
	}

	public EventSearchResult(List<EventSearchResultDetail> details) {
		super();
		this.events = details;
	}

	public boolean setSearchResult(List<PuluoEvent> inputEvents) {
		events = new ArrayList<EventSearchResultDetail>();
		for (int i = 0; i < inputEvents.size(); i++) {
			PuluoEventImpl event_impl = (PuluoEventImpl) inputEvents.get(i);
			ArrayList<String> thumbnails = new ArrayList<String>();
			ArrayList<String> images = new ArrayList<String>();
			PuluoEventInfo info = event_impl.eventInfo();
			PuluoEventLocation loc = event_impl.eventLocation();
			List<PuluoEventPoster> posters = info.poster();
			for (int j = 0; j < posters.size(); j++) {
				PuluoEventPoster p = posters.get(j);
				thumbnails.add(p.thumbnailURL());
				images.add(p.imageURL());
			}
			events.add(new EventSearchResultDetail(
					event_impl.eventUUID(),
					event_impl.statusName(), 
					info.name(),
					TimeUtils.dateTime2Millis(event_impl.eventTime()),
					loc.address(), 
					loc.city(),
					loc.phone(), 
					info.coachName(),
					info.coachUUID(), 
					thumbnails,
					event_impl.registeredUsers(),
					event_impl.capatcity(),
					info.likes(), 
					loc.latitude(),loc.longitude(), info.details(), images));
		}
		return true;
	}

	public static EventSearchResult dummy() {
		List<EventSearchResultDetail> details = new ArrayList<EventSearchResultDetail>();

		List<String> thumbnails = new ArrayList<String>();
		thumbnails.add("http://upyun.com/puluo/head.jpg");

		List<String> images = new ArrayList<String>();
		images.add("http://upyun.com/puluo/image1.jpg");
		images.add("http://upyun.com/puluo/image2.jpg");

		details.add(new EventSearchResultDetail(
				"de305d54-75b4-431b-adb2-eb6b9e546014", "open",
				"Weapons of Ass Reduction", 1427007059034L,
				"888 Happy Mansion", "beijing", "86-555-5555", "Mr. Bob Smith",
				"de305d54-75b4-431b-adb2-eb6b9e546014", thumbnails, 22, 30, 2,
				39.92889, 116.38833, "Get fit with friends.", images));
		return new EventSearchResult(details);
	}
}

class EventSearchResultDetail {
	public String event_uuid;
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

	public EventSearchResultDetail(String event_uuid, String status,
			String event_name, long event_time, String address, String city,
			String phone, String coach_name, String coach_uuid,
			List<String> thumbnail, int registered_users, int capacity,
			int likes, double latitude, double longitude, String details,
			List<String> images) {
		super();
		this.event_uuid = event_uuid;
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
		this.geo_location = new EventLocationResult(latitude, longitude);
		this.details = details;
		this.images = images;
	}
}
