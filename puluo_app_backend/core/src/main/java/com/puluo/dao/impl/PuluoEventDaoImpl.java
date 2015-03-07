package com.puluo.dao.impl;

import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventPoster;
import com.puluo.jdbc.DalTemplate;

public class PuluoEventDaoImpl extends DalTemplate implements PuluoEventDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoEvent getEventByUUID(String idevent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEventPoster[] getEventMemoris(String idevent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoEvent[] findEvents(String name, String description,
			int max_count, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
