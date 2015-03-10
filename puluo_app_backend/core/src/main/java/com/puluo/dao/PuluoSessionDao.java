package com.puluo.dao;

import com.puluo.entity.PuluoSession;

public interface PuluoSessionDao {
	
	public boolean createTable();
	
	public PuluoSession getBySessionID(String sessionID);
	
	public boolean save(String userID, String sessionID);
	
	public boolean deleteSession(String sessionID);
}
