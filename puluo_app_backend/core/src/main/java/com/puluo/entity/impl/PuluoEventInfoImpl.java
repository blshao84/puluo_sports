package com.puluo.entity.impl;

import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.enumeration.PuluoEventCategory;
import com.puluo.enumeration.PuluoEventLevel;

public class PuluoEventInfoImpl implements PuluoEventInfo {
	
	private final String uuid;
	private final String name;
	private final String description;
	private final String coach_name;
	private final String coach_uuid;
	private final String thumbnail_uuid;
	private final String details;
	private final int duration;
	private final PuluoEventLevel level;
	private final PuluoEventCategory type;

	public PuluoEventInfoImpl(String uuid) {
		this.uuid = uuid;
		this.name = null;
		this.description = null;
		this.coach_name = null;
		this.coach_uuid = null;
		this.thumbnail_uuid = null;
		this.details = null;
		this.duration = 0;
		this.level = null;
		this.type = null;
	}
	public PuluoEventInfoImpl(String uuid, String name, String description,
			String coach_name, String coach_uuid, String thumbnail_uuid,
			String details, int duration, PuluoEventLevel level, PuluoEventCategory type) {
		super();
		this.uuid = uuid;
		this.name = name;
		this.description = description;
		this.coach_name = coach_name;
		this.coach_uuid = coach_uuid;
		this.thumbnail_uuid = thumbnail_uuid;
		this.details = details;
		this.duration = duration;
		this.level = level;
		this.type = type;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuluoEventInfoImpl other = (PuluoEventInfoImpl) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


	@Override
	public String eventInfoUUID() {
		return uuid;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public String coachName() {
		return coach_name;
	}

	@Override
	public String coachUUID() {
		return coach_uuid;
	}

	@Override
	public String coachThumbnail() {
		return thumbnail_uuid;
	}

	@Override
	public String details() {
		return details;
	}

	@Override
	public int duration() {
		return duration;
	}

	@Override
	public PuluoEventLevel level() {
		return level;
	}

	@Override
	public PuluoEventCategory type() {
		return type;
	}

	@Override
	public int likes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double rating() {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public List<PuluoEventPoster> poster() {
		return poster(DaoApi.getInstance());
	}
	public List<PuluoEventPoster> poster(PuluoDSI dsi) {
		return dsi.eventPosterDao().getEventPosterByInfoUUID(uuid);
	}
}
