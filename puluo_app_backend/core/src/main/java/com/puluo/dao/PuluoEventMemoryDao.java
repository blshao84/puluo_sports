package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoEventMemory;

public interface PuluoEventMemoryDao {
	
	public boolean createTable();
	public boolean upsertEventMemory(PuluoEventMemory memory);
	public List<PuluoEventMemory> getEventMemoryByUUID(String event_uuid);
}
