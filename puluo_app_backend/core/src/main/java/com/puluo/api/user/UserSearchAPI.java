package com.puluo.api.user;

import java.util.ArrayList;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.PuluoUser;


public class UserSearchAPI extends PuluoAPI<PuluoDSI,UserSearchResult> {
	public String first_name;
	public String last_name;
	public String email;
	public String mobile;
	
	public UserSearchAPI(String first_name, String last_name, String email,
			String mobile){
		this(first_name, last_name, email, mobile, DaoApi.getInstance());
	}
	
	public UserSearchAPI(String first_name, String last_name, String email,
			String mobile, PuluoDSI dsi) {
		this.dsi = dsi;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.mobile = mobile;
	}

	@Override
	public void execute() {
		PuluoUserDaoImpl userdao = new PuluoUserDaoImpl();
		ArrayList<PuluoUser> users = userdao.findUser(first_name, last_name, email, mobile);
		UserSearchResult result = new UserSearchResult();
		result.setSearchDetails(users);
		rawResult = result;
	}
}
