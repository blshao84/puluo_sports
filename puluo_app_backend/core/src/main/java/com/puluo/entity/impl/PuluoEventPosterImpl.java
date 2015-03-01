package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;

public class PuluoEventPosterImpl implements PuluoEventPoster {
	
	private String uuid;
	private String event_info_uuid;
	private String image_url;
	
	@Override
	public String imageId() {
		// TODO Auto-generated method stub
		return uuid;
	}

	@Override
	public String imageURL() {
		// TODO Auto-generated method stub
		return image_url;
	}

	@Override
	public PuluoEventInfo eventInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
