package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;

public class PuluoSessionImpl implements PuluoSession {

	private final String user_mobile;
	private final String session_id;
	private final DateTime created_at;
	private final DateTime deleted_at;
	private final PuluoDSI dsi;

	public PuluoSessionImpl(String user_mobile, String session_id,
			DateTime created_at, DateTime deleted_at, PuluoDSI dsi) {
		super();
		this.user_mobile = user_mobile;
		this.session_id = session_id;
		this.created_at = created_at;
		this.deleted_at = deleted_at;
		this.dsi = dsi;
	}
	
	public PuluoSessionImpl(String user_mobile, String session_id,
			DateTime created_at, DateTime deleted_at) {
		this(user_mobile, session_id, created_at, deleted_at, DaoApi.getInstance());
	}

	@Override
	public String userMobile() {
		return user_mobile;
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

	@Override
	public boolean isDeleted() {
		if (deleted_at != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String userUUID() {
		return dsi.userDao().getByMobile(user_mobile).userUUID();
	}

	@Override
	public DateTime now() {
		return dsi.sessionDao().now();
	}
}
