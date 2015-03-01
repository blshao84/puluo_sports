package com.puluo.entity;


public interface PuluoEventMemory { 

	String imageId();
	String imageURL();
	PuluoEvent event();
	PuluoUser user();
	PuluoTimelinePost timeline();
}
