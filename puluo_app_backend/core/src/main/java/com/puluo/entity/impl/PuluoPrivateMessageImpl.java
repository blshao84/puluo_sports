package com.puluo.entity.impl;

import java.sql.Date;
import java.sql.Time;

import com.puluo.entity.PuluoPrivateMessage;

public class PuluoPrivateMessageImpl implements PuluoPrivateMessage{

	private String idmessage;
	private String textcontent;
	private String imgcontent;
	private Date msg_date;
	private Time msg_time;
	private int type;
	private int direction;
	private String iduser;
	private String idfriend;
	

	public PuluoPrivateMessageImpl() {}
	
	public PuluoPrivateMessageImpl(String idmessage, String textcontent,
			String imgcontent, Date msg_date, Time msg_time, int type,
			int direction, String iduser, String idfriend) {
		this.idmessage = idmessage;
		this.textcontent = textcontent;
		this.imgcontent = imgcontent;
		this.msg_date = msg_date;
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
	public Date msgDate() {
		// TODO Auto-generated method stub
		return msg_date;
	}

	@Override
	public Time msgTime() {
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

	protected String getIdMessage() {
		return idmessage;
	}

	public void setIdMessage(String idmessage) {
		this.idmessage = idmessage;
	}

	protected String getTextContent() {
		return textcontent;
	}

	public void setTextContent(String textcontent) {
		this.textcontent = textcontent;
	}

	protected String getImgContent() {
		return imgcontent;
	}

	public void setImgContent(String imgcontent) {
		this.imgcontent = imgcontent;
	}

	protected Date getMsgDate() {
		return msg_date;
	}

	public void setMsgDate(Date msg_date) {
		this.msg_date = msg_date;
	}

	protected Time getMsgTime() {
		return msg_time;
	}

	public void setMsgTime(Time msg_time) {
		this.msg_time = msg_time;
	}

	protected int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	protected String getIdUser() {
		return iduser;
	}

	public void setIdUser(String iduser) {
		this.iduser = iduser;
	}

	protected String getIdFriend() {
		return idfriend;
	}

	public void setIdFriend(String idfriend) {
		this.idfriend = idfriend;
	}
}
