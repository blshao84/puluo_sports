package com.puluo.entity.impl;
import org.joda.time.DateTime;

import com.puluo.entity.PuluoSession;

public class PuluoSessionImpl implements PuluoSession{
	
	private final String user_uuid;
	private final String session_id;
	private final DateTime created_at;
	
	
	public PuluoSessionImpl(String user_uuid, String session_id,
			DateTime created_at) {
		super();
		this.user_uuid = user_uuid;
		this.session_id = session_id;
		this.created_at = created_at;
	}

	@Override
	public String userUUID() {
		// TODO Auto-generated method stub
		return user_uuid;
	}

	@Override
	public String sessionID() {
		// TODO Auto-generated method stub
		return session_id;
	}

	@Override
	public DateTime createdAt() {
		// TODO Auto-generated method stub
		return created_at;
	}

}
