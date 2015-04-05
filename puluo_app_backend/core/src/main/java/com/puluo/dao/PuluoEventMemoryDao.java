package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoEventMemory;

public interface PuluoEventMemoryDao {
	
	public boolean createTable();
	public boolean upsertEventMemory(PuluoEventMemory memory);
	public List<PuluoEventMemory> getEventMemoryByEventUUID(String event_uuid);
	public PuluoEventMemory getEventMemoryByUUID(String uuid);
	public boolean saveEventMemory(PuluoEventMemory memory);
	public boolean updateEventMemory(PuluoEventMemory memory);
}
