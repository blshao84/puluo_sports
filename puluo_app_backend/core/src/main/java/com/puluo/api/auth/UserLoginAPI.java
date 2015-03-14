package com.puluo.api.auth;

import org.joda.time.DateTime;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserLoginResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoSession;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;

public class UserLoginAPI extends PuluoAPI<PuluoDSI, UserLoginResult> {
	public static Log log = LogFactory.getLog(UserLoginAPI.class);
	public String mobile;
	public String password;
	public String current_session_id;
	private PuluoSession session;

	public UserLoginAPI(String mobile, String password, String cur_session) {
		this(mobile, password,cur_session, DaoApi.getInstance());
	}

	public UserLoginAPI(String mobile, String password, String cur_session, PuluoDSI dsi) {
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
		this.session = dsi.sessionDao().getByMobile(mobile);
		if(session == null){
			PuluoUser user = dsi.userDao().getByMobile(mobile);
			if(user==null){
				log.error(String.format("无法找到数据库中电话为%s的用户", mobile));
				this.error = ApiErrorResult.getError(4);
				return;
			} else {
				if(user.password()!=this.password){
					log.error(String.format("用户%s的密码不匹配", mobile));
					this.error = ApiErrorResult.getError(11);
					return;
				} else {
					log.info(String.format("用户%s登录成功，保存一个新的session(id=%s)",mobile,current_session_id));
					dsi.sessionDao().save(mobile, current_session_id);
					String createdAt = TimeUtils.formatDate(DateTime.now());
					this.rawResult = new UserLoginResult(mobile,createdAt,createdAt);
					return;
				}
			}
		} else {
			log.info(String.format("找到一个已经存在的session(id=%s)",current_session_id));
			String createdAt = TimeUtils.formatDate(this.session.createdAt());
			this.rawResult = new UserLoginResult(this.session.userMobile(),createdAt,createdAt);
			if(this.current_session_id != this.session.sessionID()){
				dsi.sessionDao().save(mobile, this.current_session_id);
				dsi.sessionDao().deleteSession(this.session.sessionID());
			}
			return;
		}
	}

}
