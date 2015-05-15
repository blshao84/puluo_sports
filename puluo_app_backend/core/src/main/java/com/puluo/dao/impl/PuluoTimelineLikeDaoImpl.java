package com.puluo.dao.impl;

import com.puluo.dao.PuluoTimelineLikeDao;
import com.puluo.jdbc.DalTemplate;

public class PuluoTimelineLikeDaoImpl extends DalTemplate implements
		PuluoTimelineLikeDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String likeUserTimeline(String timeline_uuid, String user_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeLikeUserTimeline(String timeline_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
