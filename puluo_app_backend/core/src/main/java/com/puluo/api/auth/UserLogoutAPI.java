package com.puluo.api.auth;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserLogoutResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoSessionDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserLogoutAPI extends PuluoAPI<PuluoDSI, UserLogoutResult> {
	public static Log log = LogFactory.getLog(UserLogoutAPI.class);
	public PuluoSession session;

	public UserLogoutAPI(PuluoSession session) {
		this(session, DaoApi.getInstance());
	}

	public UserLogoutAPI(PuluoSession session, PuluoDSI dsi) {
		this.dsi = dsi;
		this.session = session;
	}

	@Override
	public void execute() {
		if (session == null) {
			log.error("session为空，用户已经被logout");
			this.error = ApiErrorResult.getError(13);
		} else {
			PuluoUser user = dsi.userDao().getByMobile(session.userMobile());
			String userUUID = user.userUUID();
			PuluoSessionDao sessionDao = dsi.sessionDao();
			String sessionId = session.sessionID();
			long durationSeconds = new Interval(session.createdAt(),
					DateTime.now()).toDuration().toDuration()
					.getStandardSeconds();
			boolean success = sessionDao.deleteSession(session.sessionID());
			if (success) {
				log.info(String
						.format("delete user %s session from db:session id=%s,duration=%s seconds",
								userUUID, sessionId, durationSeconds));
				this.rawResult = new UserLogoutResult(userUUID, durationSeconds);
			} else {
				log.error(String.format("删除session %s出错", sessionId));
				this.error = ApiErrorResult.getError(14);
			}
		}
	}
}
