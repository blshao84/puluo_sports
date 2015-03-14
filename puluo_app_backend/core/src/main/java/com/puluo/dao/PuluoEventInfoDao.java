package com.puluo.dao;

import com.puluo.entity.PuluoEventInfo;

public interface PuluoEventInfoDao {
	
	public boolean createTable();
	public boolean upsertEventInfo(PuluoEventInfo info);
	public PuluoEventInfo getEventInfoByUUID(String uuid);
}
