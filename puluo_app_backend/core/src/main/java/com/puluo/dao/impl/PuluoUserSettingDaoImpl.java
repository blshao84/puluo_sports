package com.puluo.dao.impl;

import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.jdbc.DalTemplate;

public class PuluoUserSettingDaoImpl extends DalTemplate implements PuluoUserSettingDao {

	@Override
	public boolean saveNewSetting(String user_uuid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAutoFriend(String user_uuid, boolean allowAutoFriend) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateTimelineVisibility(String user_uuid,
			boolean timelineVisible) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSearchability(String user_uuid, boolean searchable) {
		// TODO Auto-generated method stub
		return false;
	}

}
