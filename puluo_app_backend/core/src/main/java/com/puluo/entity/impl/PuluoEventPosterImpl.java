package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.enumeration.PuluoEventPosterType;
import com.puluo.util.Strs;

public class PuluoEventPosterImpl implements PuluoEventPoster {
	private final String uuid;
	private final String image_name;
	private final String event_info_uuid;
	private final DateTime created_at;
	private final PuluoEventPosterType poster_type;
	private final int poster_rank;

	public PuluoEventPosterImpl(String uuid, String image_name,
			String event_info_uuid, DateTime created_at,
			PuluoEventPosterType poster_type,int poster_rank) {
		super();
		this.uuid = uuid;
		this.image_name = image_name;
		this.event_info_uuid = event_info_uuid;
		this.created_at = created_at;
		this.poster_type = poster_type;
		this.poster_rank = poster_rank;
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
		return eventInfo(DaoApi.getInstance());
	}
	
	public PuluoEventInfo eventInfo(PuluoDSI dsi) {
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
	
	@Override
	public PuluoEventPosterType posterType() {
		return poster_type;
	}


	@Override
	public int rank() {
		return poster_rank;
	}
}
