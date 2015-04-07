package com.puluo.entity.impl;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;


public class PuluoEventPosterImpl implements PuluoEventPoster {
	
	private String uuid;
	private String image_url;
	private String thumbnail;
	private String event_info_uuid;
	private PuluoDSI dsi;

	public PuluoEventPosterImpl(String uuid, String image_url,
			String thumbnail, String event_info_uuid, PuluoDSI dsi) {
		super();
		this.uuid = uuid;
		this.image_url = image_url;
		this.thumbnail = thumbnail;
		this.event_info_uuid = event_info_uuid;
	}

	public PuluoEventPosterImpl(String uuid, String image_url,
			String thumbnail, String event_info_uuid) {
		this(uuid, image_url, thumbnail, event_info_uuid, DaoApi.getInstance());
	}
	
	@Override
	public String imageId() {
		return uuid;
	}

	@Override
	public String imageURL() {
		return image_url;
	}
	
	@Override
	public String thumbnail() {
		return thumbnail;
	}

	@Override
	public String eventInfoUUID() {
		return event_info_uuid;
	}

	@Override
	public PuluoEventInfo eventInfo() {
		return dsi.eventInfoDao().getEventInfoByUUID(event_info_uuid);
	}
}
