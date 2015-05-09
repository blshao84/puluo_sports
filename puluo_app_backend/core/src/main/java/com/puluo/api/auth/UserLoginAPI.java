package com.puluo.api.auth;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.PuluoUser;
import com.puluo.result.ApiErrorResult;
import com.puluo.result.user.UserLoginResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserLoginAPI extends PuluoAPI<PuluoDSI, UserLoginResult> {
	public static Log log = LogFactory.getLog(UserLoginAPI.class);
	public String mobile;
	public String password;
	public String current_session_id;
	private PuluoSession session;

	public UserLoginAPI(String mobile, String password, String cur_session) {
		this(mobile, password, cur_session, DaoApi.getInstance());
	}

	public UserLoginAPI(String mobile, String password, String cur_session,
			PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.password = password;
		this.current_session_id = cur_session;
	}

	public PuluoSession obtainSession() {
		return session;
	}

	@Override
	public void execute() {
		PuluoUser user = dsi.userDao().getByMobile(mobile);
		if (user == null) {
			log.error(String.format("无法找到数据库中电话为%s的用户", mobile));
			this.error = ApiErrorResult.getError(4);
			return;
		} else {
			if (!user.password().equals(this.password)) {
				log.error(String.format("用户%s的密码不匹配", mobile));
				this.error = ApiErrorResult.getError(11);
				return;
			} else {
				log.info(String.format("用户%s登录成功，保存一个新的session(id=%s)", mobile,
						current_session_id));
				boolean success0 = dsi.sessionDao().deleteAllSessions(mobile);
				boolean success = dsi.sessionDao().save(mobile,
						current_session_id);
				if (success && success0) {
					this.session = dsi.sessionDao().getByMobile(mobile);
					long createdAt = DateTime.now().getMillis();
					this.rawResult = new UserLoginResult(user.userUUID(), createdAt,
							createdAt);
					return;
				} else {
					log.error(String.format("为通过验证的用户保存session时出现错误"));
					this.error = ApiErrorResult.getError(12);
					return;
				}
			}
		}
	}

}
