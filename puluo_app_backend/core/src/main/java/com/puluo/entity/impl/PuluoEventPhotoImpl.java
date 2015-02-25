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

	}
