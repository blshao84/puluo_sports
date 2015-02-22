package com.puluo.dao;

import com.puluo.entity.PuluoSession;

public interface PuluoSessionDao {
	public PuluoSession getBySessionID(String sessionID);
	
	public boolean save(String sessionID);
	
	public boolean deleteSession(String sessionID);
	
	
}
