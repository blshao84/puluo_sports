package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoTimelineComment;


public interface PuluoTimelineCommentDao {
	
	public boolean createTable();
	
	public String saveTimelineComment(String timeline_uuid, String from_user_uuid, String comment);
	
	public String removeTimelineComment(String comment_uuid);
	
	public PuluoTimelineComment getByUUID(String comment_uuid);
	
	public List<PuluoTimelineComment> getByTimeline(String timeline_uuid);
	
	public List<PuluoTimelineComment> getUnreadCommentsFromUser(String user_uuid);
}
