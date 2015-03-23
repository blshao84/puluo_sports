package com.puluo.entity.impl;

import java.util.List;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.FriendRequestStatus;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;

public class PuluoFriendRequestImpl implements PuluoFriendRequest {
	private final String request_uuid;
	private final FriendRequestStatus request_status;
	private final String from_user_uuid;
	private final String to_user_uuid;
	private final DateTime created_at;
	private final DateTime updated_at;

	public PuluoFriendRequestImpl(String request_uuid,
			FriendRequestStatus request_status, String from_user_uuid,
			String to_user_uuid, DateTime created_at, DateTime updated_at) {
		super();
		this.request_uuid = request_uuid;
		this.request_status = request_status;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	@Override
	public String requestUUID() {
		return request_uuid;
	}

	@Override
	public FriendRequestStatus requestStatus() {
		return request_status;
	}

	@Override
	public List<PuluoPrivateMessage> messages() {
		return null;
	}

	public List<PuluoPrivateMessage> messages(PuluoDSI dsi) {
		return null;
	}

	@Override
	public DateTime createdAt() {
		return created_at;
	}

	@Override
	public DateTime updatedAt() {
		return updated_at;
	}

	@Override
	public PuluoUser fromUser() {
		return DaoApi.getInstance().userDao().getByUUID(from_user_uuid);
	}

	public PuluoUser fromUser(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(from_user_uuid);
	}

	@Override
	public PuluoUser toUser() {
		return DaoApi.getInstance().userDao().getByUUID(to_user_uuid);
	}

	public PuluoUser toUser(PuluoDSI dsi) {
		return dsi.userDao().getByUUID(to_user_uuid);
	}

}