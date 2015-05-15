package com.puluo.dao.impl;

import java.util.ArrayList;
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
	public ArrayList<PuluoTimelinePost> getUserTimeline(String user_uuid, String since_time) {
		// TODO Auto-generated method stub
		return null;
	}
}
