package com.puluo.dao;

import java.util.List;

import com.puluo.entity.PuluoEventInfo;

public interface PuluoEventInfoDao {
	
	public boolean createTable();
	public boolean upsertEventInfo(PuluoEventInfo info);
	public PuluoEventInfo getEventInfoByUUID(String uuid);
	public boolean saveEventInfo(PuluoEventInfo info);
	public boolean updateEventInfo(PuluoEventInfo info);
	List<PuluoEventInfo> findEventInfo(String keyword);
}
