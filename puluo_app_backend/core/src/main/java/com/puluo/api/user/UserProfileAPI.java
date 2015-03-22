package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserProfileResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class UserProfileAPI extends PuluoAPI<PuluoDSI, UserProfileResult> {
	public static Log log = LogFactory.getLog(UserProfileAPI.class);
	private final String mobileOrUUID;

	public UserProfileAPI(String mobileOrUUID) {
		this(mobileOrUUID, DaoApi.getInstance());
	}

	public UserProfileAPI(String mobileOrUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobileOrUUID = mobileOrUUID;
	}

	@Override
	public void execute() {
		PuluoUserDao userdao = dsi.userDao();
		PuluoUser user = userdao.getByMobile(mobileOrUUID);
		if (user == null) {
			user = userdao.getByUUID(mobileOrUUID);
		}
		if (user != null) {
			log.info(String.format("找到用户Mobile=%s,UUID=%s", user.mobile(),user.userUUID()));
			UserProfileResult result = new UserProfileResult(user.userUUID(),
					user.firstName(), user.lastName(), user.thumbnail(),
					user.largeImage(), user.saying(), user.likes(),
					user.banned(), user.following(), user.isCoach(),
					user.email(), String.valueOf(user.sex()),
					TimeUtils.formatDate(user.birthday()), user.occupation(),
					user.country(), user.state(), user.city(), user.zip(),
					user.createdAt().getMillis(),
					user.updatedAt().getMillis());
			rawResult = result;
		} else {
			log.error(String.format("用户%s不存在",mobileOrUUID));
			this.error = ApiErrorResult.getError(17);
		}
	}
}
