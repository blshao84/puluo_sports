package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoEventLocation;

public interface PuluoEventLocationDao {
	public boolean createTable();
	public boolean upsertEventLocation(PuluoEventLocation location);
	public PuluoEventLocation getEventLocationByUUID(String location_uuid);
	public boolean saveEventLocation(PuluoEventLocation location);
	public boolean updateEventLocation(PuluoEventLocation location);
	public List<PuluoEventLocation> findAll();
}
