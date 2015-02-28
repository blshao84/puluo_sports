package com.puluo.entity;


public interface PuluoEventMemory { 

	String idPhoto();
	String imageURL();
	PuluoEvent event();
	PuluoUser user();
	PuluoTimelinePost timeline();
}
