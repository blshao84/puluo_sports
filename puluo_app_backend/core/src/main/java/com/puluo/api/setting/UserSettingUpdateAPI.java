package com.puluo.api.setting;

import java.util.Map;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserSettingUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserSettingDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class UserSettingUpdateAPI extends PuluoAPI<PuluoDSI, UserSettingUpdateResult> {
	public static Log log = LogFactory.getLog(UserSettingUpdateAPI.class);
	public String user_uuid;
	public Boolean auto_add_friend;
	public Boolean allow_stranger_view_timeline;
	public Boolean allow_searched;

	public UserSettingUpdateAPI(String user_uuid, Map<String, String> params) {
		this(
				user_uuid,
				getBoolOrNull(params, "auto_add_friend"), 
				getBoolOrNull(params,"allow_stranger_view_timeline"), 
				getBoolOrNull(params,"allow_searched"), DaoApi.getInstance());
	}

	public UserSettingUpdateAPI(String user_uuid, Boolean auto_add_friend,
			Boolean allow_stranger_view_timeline, Boolean allow_searched) {
		this(user_uuid, auto_add_friend, allow_stranger_view_timeline, 
				allow_searched, DaoApi.getInstance());
	}

	public UserSettingUpdateAPI(String user_uuid, boolean auto_add_friend,
			boolean allow_stranger_view_timeline, boolean allow_searched,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_uuid = user_uuid;
		this.auto_add_friend = auto_add_friend;
		this.allow_stranger_view_timeline = allow_stranger_view_timeline;
		this.allow_searched = allow_searched;
	}

	@Override
	public void execute() {
		log.info(String.format("开始更新用户UUID=%s的设置", user_uuid));
		PuluoUserSettingDao settingdao = dsi.userSettingDao();
		boolean status_friend = settingdao.updateAutoFriend(user_uuid, auto_add_friend);
		boolean status_timeline = settingdao.updateTimelineVisibility(user_uuid, allow_stranger_view_timeline);
		boolean status_search = settingdao.updateSearchability(user_uuid, allow_searched);
		
		if(status_friend&&status_timeline&&status_search) {
			UserSettingUpdateResult result = new UserSettingUpdateResult(user_uuid, 
					auto_add_friend, allow_stranger_view_timeline, allow_searched);
			rawResult = result;
		} else {
			log.error(String.format("更新用户%s设置信息失败", user_uuid));
			this.error = ApiErrorResult.getError(30);
		}
	}

	private static Boolean getBoolOrNull(Map<String, String> params, String key) {
		if (params.containsKey(key)) {
			String value = params.get(key).toLowerCase();
			if (value.equals("true"))
				return true;
			else if (value.equals("false"))
				return false;
			else
				return null;
		} else
			return null;
	}
}
