package com.puluo.entity;


public interface PuluoEventMemory { 

	String imageId();
	String imageURL();
	String thumbnail();
	PuluoEvent event();
	PuluoUser user();
	PuluoTimelinePost timeline();
}
