package com.puluo.api.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.impl.PuluoUserImpl;
import com.puluo.util.TimeUtils;


public class UserProfileAPI extends PuluoAPI<PuluoDSI,UserProfileResult> {

	public String mobile;
	public String uuid;

	public UserProfileAPI(String mobile, String uuid){
		this(mobile, uuid, DaoApi.getInstance());
	}
	
	public UserProfileAPI(String mobile, String uuid, PuluoDSI dsi) {
		this.dsi = dsi;
		this.mobile = mobile;
		this.uuid = uuid;
	}

	public UserProfileAPI(String mobileOrUUID){
		this(mobileOrUUID, DaoApi.getInstance());
	}
	
	public UserProfileAPI(String mobileOrUUID, PuluoDSI dsi) {
		this.dsi = dsi;
		if (isUUID(mobileOrUUID)) {
			this.uuid = mobileOrUUID;
		}
		if (isMobile(mobileOrUUID)) {
			this.mobile = mobileOrUUID;
		}
	}

	@Override
	public void execute() {
		PuluoUserDaoImpl userdao = new PuluoUserDaoImpl();
		PuluoUserImpl user = null;
		if(!this.mobile.isEmpty())
			user = (PuluoUserImpl) userdao.getByMobile(mobile);
		else if(!this.uuid.isEmpty())
			user = (PuluoUserImpl) userdao.getByUUID(uuid);
		UserProfileResult result = new UserProfileResult(user.userUUID(),user.firstName(),user.lastName(),user.thumbnail(),
				user.largeImage(),user.saying(),user.likes(),user.banned(),user.following(),user.isCoach(),user.email(),
				String.valueOf(user.sex()),TimeUtils.formatDate(user.birthday()),user.occupation(),user.country(),user.state(),
				user.city(),user.zip(),TimeUtils.formatDate(user.createdAt()),TimeUtils.formatDate(user.updatedAt()));
		rawResult = result;
	}

	private boolean isUUID(String u) {
		// TODO
		return true;
	}
	
	public static boolean isMobile (String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
