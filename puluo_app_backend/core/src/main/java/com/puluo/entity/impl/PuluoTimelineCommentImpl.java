package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoTimelineCommentImpl implements PuluoTimelineComment {

	private final String uuid;	
	private final String timeline_uuid;
	private final String fromUser_uuid;
	private final String toUser_uuid;
	private final String comment_content;
	private final DateTime created_at;
	private final boolean read;
	//we never physically delete comments from db, only mark them as deleted
	private final boolean deleted;

	public PuluoTimelineCommentImpl(String uuid, String timeline_uuid,
			String fromUser_uuid, String toUser_uuid, String comment_content,
			DateTime creationTimestamp, boolean read, boolean deleted) {
		super();
		this.uuid = uuid;
		this.timeline_uuid = timeline_uuid;
		this.fromUser_uuid = fromUser_uuid;
		this.toUser_uuid = toUser_uuid;
		this.comment_content = comment_content;
		this.created_at = creationTimestamp;
		this.read = read;
		this.deleted = deleted;
	}

	@Override
	public String commentUUID() {
		return uuid;
	}

	@Override
	public String toUserName() {
		return toUser().name();
	}

	@Override
	public String content() {
		return comment_content;
	}

	@Override
	public boolean isRead() {
		return read;
	}

	@Override
	public DateTime createdAt() {
		return created_at;
	}
	
	@Override
	public PuluoTimelinePost timeline() {
		return timeline(DaoApi.getInstance());
	}

	public PuluoTimelinePost timeline(PuluoDSI dsi) {
		return dsi.postDao().getByUUID(timeline_uuid);
	}

	@Override
	public PuluoUser fromUser() {
		return fromUser(DaoApi.getInstance());
	}

	public PuluoUser fromUser(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(fromUser_uuid);
	}

	@Override
	public PuluoUser toUser() {
		return toUser(DaoApi.getInstance());
	}
	
	public PuluoUser toUser(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(toUser_uuid);
	}
}