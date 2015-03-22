package com.puluo.entity.impl;

import org.joda.time.DateTime;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoPrivateMessage;

public class PuluoPrivateMessageImpl implements PuluoPrivateMessage {

	private String idmessage;
	private String textcontent;
	private String imgcontent;
	private DateTime msg_time;
	private int type;
	private int direction;
	private String user_id;
	private String friend_id;
	
	private PuluoDSI dsi;

	public PuluoPrivateMessageImpl(String idmessage, String textcontent,
			String imgcontent, DateTime msg_time, int type, int direction,
			String iduser, String idfriend, PuluoDSI dsi) {
		this.idmessage = idmessage;
		this.textcontent = textcontent;
		this.imgcontent = imgcontent;
		this.msg_time = msg_time;
		this.type = type;
		this.direction = direction;
		this.user_id = iduser;
		this.friend_id = idfriend;
		this.dsi = dsi;
	}
	
	public PuluoPrivateMessageImpl(String idmessage, String textcontent,
			String imgcontent, DateTime msg_time, int type, int direction,
			String iduser, String idfriend) {
		this(idmessage, textcontent,
				imgcontent, msg_time, type, direction,
				iduser, idfriend, DaoApi.getInstance());
	}

	@Override
	public String messageId() {
		return idmessage;
	}

	@Override
	public String textContent() {
		return textcontent;
	}

	@Override
	public String imgContent() {
		return imgcontent;
	}

	@Override
	public DateTime msgTime() {
		return msg_time;
	}

	@Override
	public int type() {
		return type;
	}

	@Override
	public int direction() {
		return direction;
	}

	@Override
	public String userId() {
		return user_id;
	}

	@Override
	public String friendId() {
		return friend_id;
	}

	@Override
	public String fromUserId() {
		return friend_id;
	}

	@Override
	public String toUserid() {
		return user_id;
	}

	@Override
	public String fromUserThumbnail() {
		return dsi.userDao().getByUUID(friend_id).thumbnail();
	}

	@Override
	public String toUserThumbnail() {
		return dsi.userDao().getByUUID(user_id).thumbnail();
	}

}
