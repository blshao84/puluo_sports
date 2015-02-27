package com.puluo.entity.impl;

import org.joda.time.DateTime;

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

	public PuluoPrivateMessageImpl() {
	}

	public PuluoPrivateMessageImpl(String idmessage, String textcontent,
			String imgcontent, DateTime msg_time, int type, int direction,
			String iduser, String idfriend) {
		this.idmessage = idmessage;
		this.textcontent = textcontent;
		this.imgcontent = imgcontent;
		this.msg_time = msg_time;
		this.type = type;
		this.direction = direction;
		this.user_id = iduser;
		this.friend_id = idfriend;
	}

	@Override
	public String idMessage() {
		// TODO Auto-generated method stub
		return idmessage;
	}

	@Override
	public String textContent() {
		// TODO Auto-generated method stub
		return textcontent;
	}

	@Override
	public String imgContent() {
		// TODO Auto-generated method stub
		return imgcontent;
	}

	@Override
	public DateTime msgTime() {
		// TODO Auto-generated method stub
		return msg_time;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public int direction() {
		// TODO Auto-generated method stub
		return direction;
	}

	@Override
	public String idUser() {
		// TODO Auto-generated method stub
		return user_id;
	}

	@Override
	public String idFriend() {
		// TODO Auto-generated method stub
		return friend_id;
	}

	@Override
	public String fromUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toUserid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fromUserThumbnail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toUserThumbnail() {
		// TODO Auto-generated method stub
		return null;
	}

}
