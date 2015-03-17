package com.puluo.dao.impl;

import java.util.Date;
import java.util.List;

import com.puluo.dao.PuluoEventDao;
import com.puluo.entity.PuluoEvent;
import com.puluo.jdbc.DalTemplate;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class PuluoEventDaoImpl extends DalTemplate implements PuluoEventDao {
	
	public static Log log = LogFactory.getLog(PuluoEventDaoImpl.class);

	@Override
	public boolean createTable() {
		try {
			String createSQL = new StringBuilder().append("create table ")
				.append(super.getFullTableName())
				.append(" (id serial primary key, ")
				.append("event_uuid text unique, ")
				.append("event_time timestamp, ")
				.append("status text, ")
				.append("registered_users int, ")
				.append("capatcity int, ")
				.append("price double, ")
				.append("discounted_price double, ")
				.append("info_uuid text, ")
				.append("location_uuid text)")
				.toString();
			log.info(createSQL);
			getWriter().execute(createSQL);
			// TODO create index
		} catch (Exception e) {
			log.debug(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public PuluoEvent getEventByUUID(String idevent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoEvent> findEvents(Date event_date, String keyword, String level, 
			String sort, String sort_direction, String latitude, String longitude, int range_from) {
		// TODO Auto-generated method stub
		return null;
	}

}
