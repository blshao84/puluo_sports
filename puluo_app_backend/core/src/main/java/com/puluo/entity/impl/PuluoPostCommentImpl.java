package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.entity.PuluoTimelineComment;
import com.puluo.entity.PuluoTimelinePost;
import com.puluo.entity.PuluoUser;

public class PuluoPostCommentImpl implements PuluoTimelineComment {

	private final String comment_uuid;
	private final String uuid;	
	private final String timeline_uuid;
	private final String fromUser_uuid;
	private final String toUser_uuid;
	private final String comment_content;
	private final DateTime creationTimestamp;
	private final boolean read;

	public PuluoPostCommentImpl(String comment_uuid, String uuid, String timeline_uuid,
			String fromUser_uuid, String toUser_uuid, String comment_content,
			DateTime creationTimestamp, boolean read) {
		super();
		this.comment_uuid = comment_uuid;
		this.uuid = uuid;
		this.timeline_uuid = timeline_uuid;
		this.fromUser_uuid = fromUser_uuid;
		this.toUser_uuid = toUser_uuid;
		this.comment_content = comment_content;
		this.creationTimestamp = creationTimestamp;
		this.read = read;
	}

	@Override
	public String UUID() {
		return uuid;
	}

	@Override
	public String commentUUID() {
		return comment_uuid;
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
		return creationTimestamp;
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