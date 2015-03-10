package com.puluo.entity.impl;
import org.joda.time.DateTime;

import com.puluo.entity.PuluoSession;

public class PuluoSessionImpl implements PuluoSession{
	
	private final String user_uuid;
	private final String session_id;
	private final DateTime created_at;
	private final DateTime deleted_at;
	
	
	public PuluoSessionImpl(String user_uuid, String session_id,
			DateTime created_at, DateTime deleted_at) {
		super();
		this.user_uuid = user_uuid;
		this.session_id = session_id;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
	}

	@Override
	public String userUUID() {
		return user_uuid;
	}

	@Override
	public String sessionID() {
		return session_id;
	}

	@Override
	public DateTime createdAt() {
		return created_at;
	}

	@Override
	public DateTime deletedAt() {
		return deleted_at;
	}

}
