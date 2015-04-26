package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoEventPoster { 

	String imageId();
	String imageURL();
	String thumbnail();
	String eventInfoUUID();
	PuluoEventInfo eventInfo();
	DateTime createdAt();
}
