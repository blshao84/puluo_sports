package com.puluo.entity.impl;

import org.joda.time.DateTime;

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

	public PuluoTimelineCommentImpl(String comment_uuid, String uuid, String timeline_uuid,
			String fromUser_uuid, String toUser_uuid, String comment_content,
			DateTime creationTimestamp, boolean read) {
		super();
		this.uuid = uuid;
		this.timeline_uuid = timeline_uuid;
		this.fromUser_uuid = fromUser_uuid;
		this.toUser_uuid = toUser_uuid;
		this.comment_content = comment_content;
		this.created_at = creationTimestamp;
		this.read = read;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser fromUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuluoUser toUser() {
		// TODO Auto-generated method stub
		return null;
	}

}