package com.puluo.app;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class PuluoDBInit {

	public static void main(String[] args) {
		PuluoDSI dsi = DaoApi.getInstance();
		dsi.userSettingDao().createTable();
		dsi.userDao().createTable();
		dsi.authCodeRecordDao().createTable();
		dsi.sessionDao().createTable();
		dsi.wechatBindingDao().createTable();
		dsi.eventDao().createTable();
		dsi.eventInfoDao().createTable();
		dsi.eventLocationDao().createTable();
		dsi.eventMemoryDao().createTable();
		dsi.eventPosterDao().createTable();
		dsi.privateMessageDao().createTable();
		dsi.friendRequestDao().createTable();
		dsi.friendshipDao().createTable();
		
	}

}
