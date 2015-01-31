package com.puluo.entity.impl;

import com.puluo.entity.PuluoEventPhoto;


public class PuluoEventPhotoImpl implements PuluoEventPhoto {

	private String idphoto;
	private int sequence;
	private String url;
	private int type;
	private String idevent;
	

	public PuluoEventPhotoImpl() {}
	
	public PuluoEventPhotoImpl(String idphoto, int sequence, String url,
			int type, String idevent) {
		this.idphoto = idphoto;
		this.sequence = sequence;
		this.url = url;
		this.type = type;
		this.idevent = idevent;
	}
	
	@Override
	public String idPhoto() {
		// TODO Auto-generated method stub
		return idphoto;
	}

	@Override
	public int sequence() {
		// TODO Auto-generated method stub
		return sequence;
	}

	@Override
	public String photoURL() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	public int type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String idEvent() {
		// TODO Auto-generated method stub
		return idevent;
	}

	protected String getIdPhoto() {
		return idphoto;
	}

	public void setIdPhoto(String idphoto) {
		this.idphoto = idphoto;
	}

	protected int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	protected String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	protected int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	protected String getIdEvent() {
		return idevent;
	}

	public void setIdEvent(String idevent) {
		this.idevent = idevent;
	}
}
