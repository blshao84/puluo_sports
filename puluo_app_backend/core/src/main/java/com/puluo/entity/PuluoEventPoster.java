package com.puluo.entity;

import com.puluo.dao.PuluoEventInfoDao;


public interface PuluoEventPoster { 

	String imageId();
	String imageURL();
	String thumbnail();
	String eventInfoUUID();
	PuluoEventInfo eventInfo();
	PuluoEventInfo eventInfo(PuluoEventInfoDao eventInfoDao);
}
