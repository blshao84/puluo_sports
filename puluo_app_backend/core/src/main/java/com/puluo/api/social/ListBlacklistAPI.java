package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserBlacklist;
import com.puluo.entity.impl.PuluoUserInfo;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.CommonListAPIResult;
import com.puluo.result.user.ListResultUserDetail;
import com.puluo.result.user.ListUserPublicInfoResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class ListBlacklistAPI extends PuluoAPI<PuluoDSI, CommonListAPIResult> {
	public static Log log = LogFactory.getLog(ListBlacklistAPI.class);
	private final String user_mobile_or_uuid;

	public ListBlacklistAPI(String user_mobile_or_uuid) {
		this(user_mobile_or_uuid, DaoApi.getInstance());
	}

	public ListBlacklistAPI(String user_mobile_or_uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.user_mobile_or_uuid = user_mobile_or_uuid;
	}

	@Override
	public void execute() {
		log.info(String.format("开始查找用户:%s的Blacklist", user_mobile_or_uuid));
		PuluoUserBlacklistDao blacklistDao = dsi.blacklistDao();
		if (user()!=null) {
			List<ListResultUserDetail> userDetails = new ArrayList<ListResultUserDetail>();
			PuluoUserBlacklist blacklist = blacklistDao.getBlacklistByUUID(user().userUUID());
			if (blacklist!=null) {
				List<PuluoUserInfo> bannedUsers = blacklist.bannedUsers();
				for (PuluoUserInfo user: bannedUsers) {
					userDetails.add(new ListResultUserDetail(
							user.user_uuid,
							new ListUserPublicInfoResult(
									user.first_name, 
									user.last_name, 
									user.user_email, 
									user.user_mobile,
									user.user_thumbnail,
									user.user_saying)));
				}
			}
			CommonListAPIResult result = new CommonListAPIResult(userDetails);
			rawResult = result;
		} else {
			log.error(String.format("用户%s不存在",user_mobile_or_uuid));
			this.error = ApiErrorResult.getError(17);
		}
	}

	private PuluoUser user() {
		PuluoUser user = null;
		user = dsi.userDao().getByMobile(user_mobile_or_uuid);
		if (user != null)
			return user;
		else {
			user = dsi.userDao().getByUUID(user_mobile_or_uuid);
			return user;
		}
	}
}
