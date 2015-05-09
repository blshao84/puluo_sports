package com.puluo.api.user;

import java.util.List;

import com.puluo.api.PuluoAPI;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.result.user.UserSearchResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class UserSearchAPI extends PuluoAPI<PuluoDSI, UserSearchResult> {
	public static Log log = LogFactory.getLog(UserSearchAPI.class);
	public final String keyword;

	public UserSearchAPI(String keyword){
		this(keyword,DaoApi.getInstance());
	}
	public UserSearchAPI(String keyword,PuluoDSI dsi) {
		this.keyword = keyword.trim();
		this.dsi = dsi;
	}

	@Override
	public void execute() {
		log.info(String.format("开始根据keyword:%s条件查找用户", keyword));
		PuluoUserDao userdao = dsi.userDao();
		// Modified by Luke PuluoUserDao已修改，我默认传入了true，请根据实际情况传入值
		List<PuluoUser> users = userdao.findUser(keyword, keyword, keyword,
				keyword, false);
		log.info(String.format("找到符合条件的%s个用户", users.size()));
		UserSearchResult result = new UserSearchResult();
		result.setSearchDetails(users);
		rawResult = result;
	}
}