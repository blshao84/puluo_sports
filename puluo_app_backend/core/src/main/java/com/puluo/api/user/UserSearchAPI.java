package com.puluo.api.user;

import java.util.List;
import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserSearchResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;


public class UserSearchAPI extends PuluoAPI<PuluoDSI,UserSearchResult> {
	public static Log log = LogFactory.getLog(UserSearchAPI.class);
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
		log.info(String.format("开始根据First Name:%s,Last Name:%s,Email:%s,Mobile:%s条件查找用户",
				first_name,last_name,email,mobile));
		PuluoUserDao userdao = dsi.userDao();
		if(first_name.trim().isEmpty()) first_name=null;
		if(last_name.trim().isEmpty()) last_name=null;
		if(email.trim().isEmpty()) email=null;
		if(mobile.trim().isEmpty()) mobile=null;
		List<PuluoUser> users = userdao.findUser(first_name,last_name,email,mobile);
		log.info(String.format("找到符合条件的%s个用户",users.size()));
		UserSearchResult result = new UserSearchResult();
		result.setSearchDetails(users);
		rawResult = result;
	}
}