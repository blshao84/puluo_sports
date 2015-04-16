package com.puluo.entity.impl;

import com.puluo.entity.WechatMediaResource;

public class WechatMediaResourceImpl implements WechatMediaResource {
	private final String media_id;
	private final String media_name;
	private final String media_type;
	private final String media_link;
	
	
	public WechatMediaResourceImpl(String media_id, String name,
			String media_type, String media_link) {
		super();
		this.media_id = media_id;
		this.media_name = name;
		this.media_type = media_type;
		this.media_link = media_link;
	}

	@Override
	public String mediaID() {
		return media_id;
	}

	@Override
	public String wechatName() {
		return media_name;
	}

	@Override
	public String wechatMediaType() {
		return media_type;
	}

	@Override
	public String wechatLink() {
		return media_link;
	}

}
