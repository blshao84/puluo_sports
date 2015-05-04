package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoEventPoster { 

	String imageId();
	String imageURL();
	String thumbnailURL();
	String imageName();
	String eventInfoUUID();
	PuluoEventInfo eventInfo();
	DateTime createdAt();
}
