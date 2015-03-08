package com.puluo.dao.impl;

import java.util.ArrayList;
import java.util.Date;

import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.PuluoEvent;
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
	public ArrayList<PuluoEvent> findEvents(Date event_date, String keyword, String level, 
			String sort, String sort_direction, String latitude, String longitude, int range_from) {
		// TODO Auto-generated method stub
		return null;
	}

}
