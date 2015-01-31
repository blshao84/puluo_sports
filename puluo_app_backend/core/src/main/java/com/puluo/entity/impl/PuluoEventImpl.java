package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoEvent;


public class PuluoEventImpl implements PuluoEvent {

	private String idevent;
	private Date event_date;
	private Time event_time;
	private String name;
	private int duration;
	private String description;
	private int level;
	private float rating;
	private int type;
	private String idlocation;
	private String[] event_photoid;
	

	public PuluoEventImpl() {}
	
	public PuluoEventImpl(String idevent, Date event_date, Time event_time,
			String name, int duration, String description, int level, float rating,
			int type, String idlocation, String[] event_photoid) {
		this.idevent = idevent;
		this.event_date = event_date;
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
	public Date eventDate() {
		// TODO Auto-generated method stub
		return event_date;
	}

	@Override
	public Time eventTime() {
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

	protected String getIdEvent() {
		return idevent;
	}

	public void setIdEvent(String idevent) {
		this.idevent = idevent;
	}

	protected Date getEventDate() {
		return event_date;
	}

	public void setEventDate(Date event_date) {
		this.event_date = event_date;
	}

	protected Time getEventTime() {
		return event_time;
	}

	public void setEventTime(Time event_time) {
		this.event_time = event_time;
	}

	protected int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	protected String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	protected int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	protected float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	protected int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected String getIdLocation() {
		return idlocation;
	}

	public void setIdLocation(String idlocation) {
		this.idlocation = idlocation;
	}

	protected String[] getEventPhotoId() {
		return event_photoid;
	}

	public void setEventPhotoId(String[] event_photoid) {
		this.event_photoid = event_photoid;
	}
}
