package com.puluo.dao;

import com.puluo.entity.PuluoUserSetting;

public interface PuluoUserSettingDao {
	
	public boolean createTable();
	
	public boolean saveNewSetting(String user_uuid);
	
	public boolean updateAutoFriend(String user_uuid, boolean allowAutoFriend);
	
	public boolean updateTimelineVisibility(String user_uuid, boolean timelineVisible);
	
	public boolean updateSearchability(String user_uuid, boolean searchable);
	
	public PuluoUserSetting getByUserUUID(String user_uuid);
}
