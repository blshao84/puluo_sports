package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;


public class PuluoEventPosterImpl implements PuluoEventPoster {
	
	private String uuid;
	private String image_url;
	private String thumbnail;
	private String event_info_uuid;
	private DateTime created_at;
	private PuluoDSI dsi;

	public PuluoEventPosterImpl(String uuid, String image_url,
			String thumbnail, String event_info_uuid, DateTime created_at, PuluoDSI dsi) {
		super();
		this.uuid = uuid;
		this.image_url = image_url;
		this.thumbnail = thumbnail;
		this.event_info_uuid = event_info_uuid;
		this.created_at = created_at;
	}

	public PuluoEventPosterImpl(String uuid, String image_url,
			String thumbnail, String event_info_uuid, DateTime created_at) {
		this(uuid, image_url, thumbnail, event_info_uuid, created_at, DaoApi.getInstance());
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

	@Override
	public DateTime createdAt() {
		return created_at;
	}
}
