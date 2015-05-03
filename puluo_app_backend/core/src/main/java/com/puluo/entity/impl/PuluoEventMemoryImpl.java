package com.puluo.entity.impl;

import com.puluo.config.Configurations;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventMemory;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Strs;

public class PuluoEventMemoryImpl implements PuluoEventMemory {

	private final String uuid;
	private final String image_url;
	private final String thumbnail;
	private final String event_uuid;
	private final String user_uuid;
	private final String timeline_uuid;

	public PuluoEventMemoryImpl(String uuid, String image_url,
			String thumbnail, String event_uuid, String user_uuid,
			String timeline_uuid) {
		super();
		this.uuid = uuid;
		this.image_url = image_url;
		this.thumbnail = thumbnail;
		this.event_uuid = event_uuid;
		this.user_uuid = user_uuid;
		this.timeline_uuid = timeline_uuid;
	}
	
	@Override
	public String imageId() {
		return uuid;
	}

	@Override
	public String imageURL() {
		if (image_url.equals("")) {
			return Strs.join(Configurations.emptyImage);
		} else {
			return Strs.join(Configurations.imageServer, image_url);
		}
	}

	@Override
	public String thumbnail() {
		return Strs.join(imageURL(),"!small");
	}

	@Override
	public PuluoEvent event() {
		return DaoApi.getInstance().eventDao().getEventByUUID(event_uuid);
	}

	@Override
	public PuluoUser user() {
		return DaoApi.getInstance().userDao().getByUUID(user_uuid);
	}

	@Override
	public PuluoTimelinePost timeline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String eventId() {
		return event_uuid;
	}

	@Override
	public String userId() {
		return user_uuid;
	}

	@Override
	public String timelineId() {
		return timeline_uuid;
	}
}
