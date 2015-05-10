package com.puluo.api.social;

import java.util.ArrayList;
import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserBlacklistDao;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserBlacklist;
import com.puluo.entity.impl.PuluoUserInfo;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.BlacklistUpdateResult;
import com.puluo.result.user.ListResultUserDetail;
import com.puluo.result.user.ListUserPublicInfoResult;

public class UpdateBlacklistAPI extends
		PuluoAPI<PuluoDSI, BlacklistUpdateResult> {
	private final String requester_uuid;
	private final String target_uuid;
	private final String op_type;

	public UpdateBlacklistAPI(String requester_uuid, String target_uuid,
			String op_type) {
		this(requester_uuid, target_uuid, op_type, DaoApi.getInstance());
	}

	public UpdateBlacklistAPI(String requester_uuid, String target_uuid,
			String op_type, PuluoDSI dsi) {
		super();
		this.requester_uuid = requester_uuid;
		this.target_uuid = target_uuid;
		this.op_type = op_type;
		this.dsi = dsi;
	}

	@Override
	public void execute() {
		PuluoUserDao userDao = dsi.userDao();
		PuluoUser requester = userDao.getByUUID(requester_uuid);
		PuluoUser target = userDao.getByUUID(target_uuid);
		if (requester != null && target != null) {
			PuluoUserBlacklistDao blacklistDao = dsi.blacklistDao();
			PuluoUserBlacklist blacklist;
			if (this.isBan()) {
				if (requester_uuid.equals(target_uuid)) {
					this.error = ApiErrorResult.getError(50);
					return;
				} else {
					blacklist = blacklistDao.banUser(requester_uuid,
							target_uuid);
				}
			} else if (this.isFree()) {
				blacklist = blacklistDao.freeUser(requester_uuid, target_uuid);
			} else {
				this.error = ApiErrorResult.getError(48);
				return;
			}
			if (blacklist != null) {
				List<ListResultUserDetail> details = new ArrayList<ListResultUserDetail>();
				for (PuluoUserInfo bannedUser : blacklist.bannedUsers()) {
					ListUserPublicInfoResult pubInfo = new ListUserPublicInfoResult(
							bannedUser.first_name, bannedUser.last_name,
							bannedUser.user_email, bannedUser.user_mobile,
							bannedUser.user_thumbnail, bannedUser.user_saying);
					details.add(new ListResultUserDetail(bannedUser.user_uuid,
							pubInfo));
				}
				this.rawResult = new BlacklistUpdateResult(requester_uuid,
						true, details);
			} else {
				this.error = ApiErrorResult.getError(49);
			}
		} else {
			this.error = ApiErrorResult.getError(47);
		}

	}

	private boolean isBan() {
		return op_type.equals("ban");
	}

	private boolean isFree() {
		return op_type.equals("free");
	}

}
