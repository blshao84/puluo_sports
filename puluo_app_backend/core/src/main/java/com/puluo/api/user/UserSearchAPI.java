package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSearchResult;

public class UserSearchAPI extends PuluoAPI<UserSearchResult> {
	public String first_name;
	public String last_name;
	public String email;
	public String mobile;
	
	public UserSearchAPI(String first_name, String last_name, String email,
			String mobile) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.mobile = mobile;
	}

	@Override
	public UserSearchResult rawResult() {
		// TODO Auto-generated method stub
		return null;
	}

}
