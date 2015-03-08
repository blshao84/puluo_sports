package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.impl.PuluoUserImpl;


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
		if (isMobile(mobileOrUUID)) {
			this.mobile = mobileOrUUID;
		}
		if (isUUID(mobileOrUUID)) {
			this.uuid = mobileOrUUID;
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
		UserProfileResult result = new UserProfileResult(user.userUUID(),user.firstName(),user.lastName(),
				user.thumbnail(),user.largeImage(),user.saying(),user.likes(),user.banned(),user.following(),
				user.isCoach(),user.email(),String.valueOf(user.sex()),user.birthday().toString(),user.occupation(),
				user.country(),user.state(),user.city(),user.zip(),user.createdAt().toString(),user.updatedAt().toString());//FIXME: THIS IS WRONG!!! we can't just use toString() to format a DateTime ...
		rawResult = result;
	}

	private boolean isMobile(String m) {
		// TODO
		return true;
	}

	private boolean isUUID(String u) {
		// TODO
		return true;
	}
}
