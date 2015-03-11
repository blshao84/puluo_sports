package com.puluo.dao;

public interface PuluoUserSettingDao {
	
	public boolean saveNewSetting(String user_uuid);
	
	public boolean updateAutoFriend(String user_uuid, boolean allowAutoFriend);
	
	public boolean updateTimelineVisibility(String user_uuid, boolean timelineVisible);
	
	public boolean updateSearchability(String user_uuid, boolean searchable);
}
