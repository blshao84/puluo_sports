package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserSettingResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUserSetting;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class UserSettingAPI extends PuluoAPI<PuluoDSI, UserSettingResult> {
	public static Log log = LogFactory.getLog(UserSettingAPI.class);
	public String user_uuid;

	public UserSettingAPI(String user_uuid) {
		this(user_uuid, DaoApi.getInstance());
	}

	public UserSettingAPI(String mobileOrUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户UUID=%s设置", user_uuid));
		PuluoUserSettingDao settingdao = dsi.userSettingDao();
		PuluoUserSetting setting = settingdao.getByUserUUID(user_uuid);
		if(setting != null) {
			UserSettingResult result = new UserSettingResult(user_uuid, setting.autoAddFriend(), 
					setting.isTimelinePublic(), setting.isSearchable());
			rawResult = result;
		} else {
			log.error(String.format("用户%s设置不存在", user_uuid));
			this.error = ApiErrorResult.getError(29);
		}
	}
}
