package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoEvent;


public class PuluoEventImpl implements PuluoEvent {

	private String idevent;
	private DateTime event_time;
	private String name;
	private int duration;
	private String description;
	private int level;
	private float rating;
	private int type;
	private String idlocation;
	private String[] event_photoid;
	

	public PuluoEventImpl() {}
	
	public PuluoEventImpl(String idevent, DateTime event_time,
			String name, int duration, String description, int level, float rating,
			int type, String idlocation, String[] event_photoid) {
		this.idevent = idevent;
		this.event_time = event_time;
		this.name = name;
		this.duration = duration;
		this.description = description;
		this.level = level;
		this.rating = rating;
		this.type = type;
		this.idlocation = idlocation;
		this.event_photoid = event_photoid;
	}
	
	@Override
	public String idEvent() {
		// TODO Auto-generated method stub
		return idevent;
	}


	@Override
	public DateTime eventTime() {
		// TODO Auto-generated method stub
		return event_time;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int duration() {
		// TODO Auto-generated method stub
		return duration;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return description;
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		return level;
	}

	@Override
	public float rating() {
		// TODO Auto-generated method stub
		return rating;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String idLocation() {
		// TODO Auto-generated method stub
		return idlocation;
	}

	@Override
	public String[] eventPhotoId() {
		// TODO Auto-generated method stub
		return event_photoid;
	}

}
