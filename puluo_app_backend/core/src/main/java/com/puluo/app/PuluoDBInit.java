package com.puluo.app;

import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class PuluoDBInit {

	public static void main(String[] args) {
		PuluoDSI dsi = DaoApi.getInstance();
		//dsi.userDao().createTable();
		//dsi.authCodeRecordDao().createTable();
		//dsi.sessionDao().createTable();
		dsi.wechatBindingDao().createTable();
	}

}
