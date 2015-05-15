package com.puluo.dao.impl;

import java.util.List;

import com.puluo.dao.PuluoTimelineDao;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.jdbc.DalTemplate;


public class PuluoTimelineDaoImpl extends DalTemplate implements PuluoTimelineDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<PuluoTimelinePost> getUserTimeline(String user_uuid,
			String since_time, int limit, int offset) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveTimeline(PuluoTimelinePost timeline) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoTimelinePost getByUUID(String uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoTimelinePost getByUserAndEvent(String user_uuid,
			String event_uuid) {
		// TODO Auto-generated method stub
		return null;
	}
}
