package com.puluo.dao.impl;

import com.puluo.dao.PuluoPostDao;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.jdbc.DalTemplate;

public class PuluoPostDaoImpl extends DalTemplate implements PuluoPostDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PuluoTimelinePost[] getUserTimeline(String userUUID) {
		// TODO Auto-generated method stub
		return null;
	}

}
