package com.puluo.entity;

import java.sql.Date;
import java.sql.Time;


public interface EventInterface {

	Date getDate(String idevent);
	
	String setDate(String idevent, Date date);
	
	Time getTime(String idevent);
	
	String setTime(String idevent, Time time);
	
	String getName(String idevent);
	
	String setName(String idevent, String name);
	
	String getDuration(String idevent);
	
	String setDuration(String idevent, int duration);
	
	String getDescription(String idevent);
	
	String setDescription(String idevent, String description);
	
	String getLevel(String idevent);
	
	String setLevel(String idevent, String level);
	
	float getRating(String idevent);
	
	String setRating(String idevnet, float rating);

	int getType(String idevent);
	
	String setType(String idevent, int type);
	
	String getLocation(String idevent);
	
	String setLocation(String idevent, String idlocation);
	
	String[] getEventPhotoId(String idevent);
	
	String setEventPhotoId(String idevent, String[] eventphotoid);
	
	int getSequence(String photoid);
	
	String setSequence(String photoid, int seq);
	
	String getPhotoURL(String photoid);
	
	String setPhotoURL(String photoid, String url);
	
	int getPhotoType(String photoid);
	
	String setPhotoType(String photoid, int type);
	
	String[] findEventId(String name, int duration, String level, String location, String type);

}
