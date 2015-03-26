package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoEventPoster;

public interface PuluoEventPosterDao {
	public boolean createTable();
	public boolean saveEventPhoto(PuluoEventPoster photo);
	public List<PuluoEventPoster> getEventPoster(String event_info_uuid);
	public boolean updateEventPhoto(PuluoEventPoster photo);
}
