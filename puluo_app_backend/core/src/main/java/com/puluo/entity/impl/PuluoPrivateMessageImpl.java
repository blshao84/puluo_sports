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
	private String iduser;
	private String idfriend;

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
		this.iduser = iduser;
		this.idfriend = idfriend;
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
		return iduser;
	}

	@Override
	public String idFriend() {
		// TODO Auto-generated method stub
		return idfriend;
	}

}
