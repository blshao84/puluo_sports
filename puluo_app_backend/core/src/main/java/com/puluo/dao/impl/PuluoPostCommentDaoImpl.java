package com.puluo.dao.impl;

import com.puluo.dao.PuluoPostCommentDao;
import com.puluo.jdbc.DalTemplate;

public class PuluoPostCommentDaoImpl extends DalTemplate implements
		PuluoPostCommentDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String commentUserTimeline(String timeline_uuid, String reply_to, String comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeCommentUserTimeline(String comment_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
