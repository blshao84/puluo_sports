package com.puluo.entity;

import org.joda.time.DateTime;

public interface PuluoTimelineComment {

	public String commentUUID();

	public PuluoTimelinePost timeline();

	public PuluoUser fromUser();

	public PuluoUser toUser();

	public String toUserName();

	public String content();

	public boolean isRead();

	public DateTime createdAt();
}
