package com.puluo.entity.impl;

import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoEventMemoryImpl implements PuluoEventMemory {

	private String uuid;
	private String image_url;
	private String thumbnail;
	private String event_uuid;
	private String user_uuid;
	private String timeline_uuid;
	
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
	public String thumbnail() {
		// TODO Auto-generated method stub
		return thumbnail;
	}

	@Override
	public PuluoEvent event() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser user() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoTimelinePost timeline() {
		// TODO Auto-generated method stub
		return null;
	}
}
