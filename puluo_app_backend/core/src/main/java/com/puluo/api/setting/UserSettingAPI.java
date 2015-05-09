package com.puluo.api.setting;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserSetting;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.UserSettingResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserSettingAPI extends PuluoAPI<PuluoDSI, UserSettingResult> {
	public static Log log = LogFactory.getLog(UserSettingAPI.class);
	public String mobile_or_uuid;

	public UserSettingAPI(String mobile_or_uuid) {
		this(mobile_or_uuid, DaoApi.getInstance());
	}

	public UserSettingAPI(String mobile_or_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile_or_uuid = mobile_or_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户UUID=%s设置", mobile_or_uuid));
		PuluoUser user = user();
		if (user == null) {
			log.error(String.format("用户%s不存在", mobile_or_uuid));
			this.error = ApiErrorResult.getError(29);
		} else {
			PuluoUserSettingDao settingdao = dsi.userSettingDao();
			PuluoUserSetting setting = settingdao.getByUserUUID(mobile_or_uuid);
			if (setting != null) {
				UserSettingResult result = new UserSettingResult(
						mobile_or_uuid, setting.autoAddFriend(),
						setting.isTimelinePublic(), setting.isSearchable());
				rawResult = result;
			} else {
				log.error(String.format("用户%s设置不存在", mobile_or_uuid));
				this.error = ApiErrorResult.getError(29);
			}
		}
	}

	public String legacyResult() {
		return rawResult.legacyResult();
	}
	private PuluoUser user() {
		PuluoUserDao userdao = dsi.userDao();
		PuluoUser user = userdao.getByMobile(mobile_or_uuid);
		if (user == null) {
			user = userdao.getByUUID(mobile_or_uuid);
		}
		return user;
	}
}
