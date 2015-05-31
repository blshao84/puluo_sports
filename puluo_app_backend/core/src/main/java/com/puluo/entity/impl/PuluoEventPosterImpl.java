package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.util.Strs;

public class PuluoEventPosterImpl implements PuluoEventPoster {
	private final String uuid;
	private final String image_name;
	private final String event_info_uuid;
	private final DateTime created_at;
	private PuluoDSI dsi;

	public PuluoEventPosterImpl(String uuid, String image_name,
			String event_info_uuid, DateTime created_at, PuluoDSI dsi) {
		super();
		this.uuid = uuid;
		this.image_name = image_name;
		this.event_info_uuid = event_info_uuid;
		this.created_at = created_at;
	}

	public PuluoEventPosterImpl(String uuid, String image_name,
			String event_info_uuid, DateTime created_at) {
		this(uuid, image_name, event_info_uuid, created_at, DaoApi.getInstance());
	}

	@Override
	public String imageId() {
		return uuid;
	}

	@Override
	public String imageURL() {
		if (image_name.equals("")) {
			return Strs.join(Configurations.emptyImage);
		} else {
			return Strs.join(Configurations.imgHttpLink(image_name),"!rect");
		}
	}

	@Override
	public String thumbnailURL() {
		return Strs.join(Configurations.imgHttpLink(image_name), "");
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

	@Override
	public String imageName() {
		return image_name;
	}
}
