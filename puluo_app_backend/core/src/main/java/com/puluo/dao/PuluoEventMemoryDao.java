package com.puluo.dao;

import java.util.List;
import com.puluo.entity.PuluoEventMemory;


public interface PuluoEventMemoryDao {
	
	public boolean createTable();
	
	public List<PuluoEventMemory> getEventMemoryByUUID(String event_uuid);
}
