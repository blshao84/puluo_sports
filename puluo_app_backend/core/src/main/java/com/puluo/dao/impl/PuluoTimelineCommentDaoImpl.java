package com.puluo.dao.impl;

import java.util.List;

import com.puluo.dao.PuluoTimelineCommentDao;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.jdbc.DalTemplate;

public class PuluoTimelineCommentDaoImpl extends DalTemplate implements
		PuluoTimelineCommentDao {

	@Override
	public boolean createTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String saveTimelineComment(String timeline_uuid, String reply_to, String comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeTimelineComment(String comment_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoTimelineComment getByUUID(String comment_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoTimelineComment> getByTimeline(String timeline_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuluoTimelineComment> getUnreadCommentsFromUser(String user_uuid) {
		// TODO Auto-generated method stub
		return null;
	}

}
