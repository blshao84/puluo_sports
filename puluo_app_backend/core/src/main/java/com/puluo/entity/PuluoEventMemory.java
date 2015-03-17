package com.puluo.entity;


public interface PuluoEventMemory { 

	String imageId();
	String imageURL();
	String thumbnail();
	String eventId(); // luke
	String userId(); // luke
	String timelineId(); // luke
	PuluoEvent event();
	PuluoUser user();
	PuluoTimelinePost timeline();
}
