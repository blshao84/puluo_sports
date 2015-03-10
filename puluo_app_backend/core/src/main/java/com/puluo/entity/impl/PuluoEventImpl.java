package com.puluo.entity.impl;

import java.util.List;
import org.joda.time.DateTime;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventLocation;
import com.puluo.entity.PuluoEventMemory;


public class PuluoEventImpl implements PuluoEvent {
	private final String uuid;
	private final DateTime event_time;
	private final String status; // TODO: should be strong typed
	//private final String[] images;
	//private final String[] memories;
	private final int registeredUsers;
	private final int capatcity;
	private final Double price;
	private final Double discountedPrice;
	private final String info_uuid;
	private final String location_uuid;

	public PuluoEventImpl(String uuid, DateTime event_time, String status, 
			int registeredUsers, int capatcity, Double price, Double discountedPrice, 
			String info_uuid, String location_uuid) {

		this.uuid = uuid;
		this.event_time = event_time;
		this.status = status;
		//this.images = images;
		//this.memories = memories;
		this.registeredUsers = registeredUsers;
		this.capatcity = capatcity;
		this.price = price;
		this.discountedPrice = discountedPrice;
		this.info_uuid = info_uuid;
		this.location_uuid = location_uuid;
	}

	@Override
	public String eventUUID() {
		// TODO Auto-generated method stub
		return uuid;
	}

	@Override
	public DateTime eventTime() {
		// TODO Auto-generated method stub
		return event_time;
	}

	@Override
	public String status() {
		// TODO Auto-generated method stub
		return status;
	}

	@Override
	public int registeredUsers() {
		// TODO Auto-generated method stub
		return registeredUsers;
	}

	@Override
	public int capatcity() {
		// TODO Auto-generated method stub
		return capatcity;
	}

	@Override
	public Double price() {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public Double discount() {
		// TODO Auto-generated method stub
		return discountedPrice / price;
	}

	@Override
	public Double discountedPrice() {
		// TODO Auto-generated method stub
		return discountedPrice;
	}

	@Override
	public PuluoEventInfo eventInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEventLocation eventLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoEventMemory> memory() {
		// TODO Auto-generated method stub
		return null;
	}
}
