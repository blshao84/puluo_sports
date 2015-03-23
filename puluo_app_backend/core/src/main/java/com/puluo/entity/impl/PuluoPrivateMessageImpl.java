package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoFriendRequest;
import com.puluo.entity.PuluoMessageType;
import com.puluo.entity.PuluoPrivateMessage;
import com.puluo.entity.PuluoUser;

public class PuluoPrivateMessageImpl implements PuluoPrivateMessage {

	private final String message_uuid;
	private final String content;
	private final PuluoMessageType message_type;
	private final String friend_request_uuid;
	private final String from_user_uuid;
	private final String to_user_uuid;
	private final DateTime created_at;


	public PuluoPrivateMessageImpl(String message_uuid, String content,
			DateTime created_at, PuluoMessageType message_type, String friend_request_uuid,
			String from_user_uuid, String to_user_uuid) {
		this.message_uuid = message_uuid;
		this.content = content;
		this.created_at = created_at;
		this.to_user_uuid = to_user_uuid;
		this.from_user_uuid = from_user_uuid;
		this.message_type = message_type;
		this.friend_request_uuid = friend_request_uuid;
	}


	@Override
	public String messageUUID() {
		
		return message_uuid;
	}


	@Override
	public String content() {
		
		return content;
	}


	@Override
	public DateTime createdAt() {
		
		return created_at;
	}


	@Override
	public PuluoMessageType messageType() {
		
		return message_type;
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


	@Override
	public PuluoFriendRequest friendRequest() {
		// TODO Auto-generated method stub
		return null;
	}
	public PuluoFriendRequest friendRequest(PuluoDSI dsi) {
		// TODO Auto-generated method stub
		return null;
	}

}
