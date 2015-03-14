package com.puluo.entity.impl;

import com.puluo.dao.PuluoEventInfoDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.entity.PuluoEventPoster;


public class PuluoEventPosterImpl implements PuluoEventPoster {
	
	private String uuid;
	private String image_url;
	private String thumbnail;
	private String event_info_uuid;

	public PuluoEventPosterImpl(String uuid, String image_url,
			String thumbnail, String event_info_uuid) {
		super();
		this.uuid = uuid;
		this.image_url = image_url;
		this.thumbnail = thumbnail;
		this.event_info_uuid = event_info_uuid;
	}
	
	@Override
	public String imageId() {
		return uuid;
	}

	@Override
	public String imageURL() {
		return image_url;
	}
	
	@Override
	public String thumbnail() {
		return thumbnail;
	}

	@Override
	public String eventInfoUUID() {
		return event_info_uuid;
	}

	@Override
	public PuluoEventInfo eventInfo() {
		return eventInfo(DaoApi.getInstance().eventInfoDao());
	}

	@Override
	public PuluoEventInfo eventInfo(PuluoEventInfoDao eventInfoDao) {
		PuluoEventInfoDao puluoEventInfoDao;
		if (eventInfoDao!=null) {
			puluoEventInfoDao = eventInfoDao;
		} else {
			puluoEventInfoDao = DaoApi.getInstance().eventInfoDao();
		}
		return puluoEventInfoDao.getEventInfoByUUID(event_info_uuid);
	}
}
