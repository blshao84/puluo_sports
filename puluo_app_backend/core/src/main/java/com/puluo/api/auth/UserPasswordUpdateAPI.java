package com.puluo.api.auth;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserPasswordUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;

public class UserPasswordUpdateAPI extends PuluoAPI<PuluoDSI,UserPasswordUpdateResult> {
	public String userUUID;
	public String password;
	public String new_password;
	
	public UserPasswordUpdateAPI(String userUUID,String password, String new_password){
		this(userUUID, password, new_password, DaoApi.getInstance());
	}
	
	public UserPasswordUpdateAPI(String userUUID,String password, String new_password, PuluoDSI dsi) {
		this.dsi = dsi;
		this.userUUID = userUUID;
		this.password = password;
		this.new_password = new_password;
	}


	@Override
	public void execute() {
		PuluoUserDao userDao = dsi.userDao();
		PuluoUser user = userDao.getByUUID(userUUID);
		if(user == null){
			this.error = ApiErrorResult.getError(18);
			return;
		} else {
			String oldPassword = user.password();
			if(!oldPassword.equals(password)){
				this.error = ApiErrorResult.getError(19);
				return;
			}else{
				boolean success = userDao.updatePassword(user, new_password);
				if(success){
					this.rawResult = new UserPasswordUpdateResult(password,new_password);
					return;
				}else{
					this.error = ApiErrorResult.getError(20);
					return;
				}
			}
		}
	}

}
