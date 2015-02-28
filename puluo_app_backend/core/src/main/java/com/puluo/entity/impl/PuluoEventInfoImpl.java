package com.puluo.entity.impl;

import java.util.List;

import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;

public class PuluoEventInfoImpl implements PuluoEventInfo {
	
	private String uuid;
	private String name;
	private String description;
	private String coach_uuid;
	private String thumbnail_uuid;
	private String details;
	private int duration;
	private int level;
	private int type;
	
	@Override
	public String eventInfoUUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String coachName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String coachUuid() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String details() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int duration() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int level() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int likes() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float rating() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PuluoEventPoster> poster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEventPoster thumbnail() {
		// TODO Auto-generated method stub
		return null;
	}

}
