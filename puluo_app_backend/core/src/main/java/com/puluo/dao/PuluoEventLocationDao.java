package com.puluo.dao;

import com.puluo.entity.PuluoEventLocation;

public interface PuluoEventLocationDao {
	public boolean createTable();
	public boolean upsertEventLocation(PuluoEventLocation location);
	public PuluoEventLocation getEventLocationByUUID(String location_uuid);
}
