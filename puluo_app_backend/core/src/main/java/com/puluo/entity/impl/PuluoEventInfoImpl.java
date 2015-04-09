package com.puluo.entity.impl;

import java.util.List;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;

public class PuluoEventInfoImpl implements PuluoEventInfo {
	
	private String uuid;
	private String name;
	private String description;
	private String coach_name;
	private String coach_uuid;
	private String thumbnail_uuid;
	private String details;
	private int duration;
	private int level;
	private int type;
	private PuluoDSI dsi;

	public PuluoEventInfoImpl(String uuid, String name, String description,
			String coach_name, String coach_uuid, String thumbnail_uuid,
			String details, int duration, int level, int type, PuluoDSI dsi) {
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
		this.dsi = dsi;
	}
	
	public PuluoEventInfoImpl(String uuid, String name, String description,
			String coach_name, String coach_uuid, String thumbnail_uuid,
			String details, int duration, int level, int type) {
		this(uuid, name, description, coach_name, coach_uuid, thumbnail_uuid, details, duration, level, type, DaoApi.getInstance());
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
	public int level() {
		return level;
	}

	@Override
	public int type() {
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
		return dsi.eventPosterDao().getEventPosterByInfoUUID(uuid);
	}
}
