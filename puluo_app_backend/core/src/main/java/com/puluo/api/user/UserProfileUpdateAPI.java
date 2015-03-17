package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.UserProfileUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.PuluoUserDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoUser;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.TimeUtils;


public class UserProfileUpdateAPI extends PuluoAPI<PuluoDSI, UserProfileUpdateResult> {
	public static Log log = LogFactory.getLog(UserProfileUpdateAPI.class);
	public String uuid;
	public String first_name;
	public String last_name;
	public String thumbnail;
	public String large_image;
	public String saying;
	public String email;
	public String sex;
	public String birthday;
	public String country;
	public String state;
	public String city;
	public String zip;

	public UserProfileUpdateAPI(String uuid, String first_name, String last_name,
			String thumbnail, String large_image, String saying, String email,
			String sex, String birthday, String country, String state,
			String city, String zip) {
		this(uuid, first_name, last_name, thumbnail, large_image, saying, email, sex,
				birthday, country, state, city, zip, DaoApi.getInstance());
	}

	public UserProfileUpdateAPI(String uuid, String first_name, String last_name,
			String thumbnail, String large_image, String saying, String email,
			String sex, String birthday, String country, String state,
			String city, String zip, PuluoDSI dsi) {
		this.dsi = dsi;
		this.uuid = uuid;
		this.first_name = first_name;
		this.last_name = last_name;
		this.thumbnail = thumbnail;
		this.large_image = large_image;
		this.saying = saying;
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zip = zip;
	}

	@Override
	public void execute() {
		log.info(String.format("开始更新用户UUID=%s的个人信息", uuid));
		PuluoUserDao userdao = dsi.userDao();
		PuluoUser curuser = userdao.getByUUID(uuid);
		PuluoUser upduser = userdao.updateProfile(curuser,first_name,last_name,thumbnail,
				large_image,saying,email,sex,birthday,country,state,city,zip);
		if (upduser!=null) {
			UserProfileUpdateResult result = new UserProfileUpdateResult(upduser.userUUID(),
					upduser.firstName(),upduser.lastName(),upduser.thumbnail(),upduser.largeImage(), 
					upduser.saying(),upduser.email(),String.valueOf(upduser.sex()), 
					TimeUtils.formatDate(upduser.birthday()),upduser.occupation(),
					upduser.country(),upduser.state(),upduser.city(),upduser.zip(), 
					TimeUtils.formatDate(upduser.createdAt()),TimeUtils.formatDate(upduser.updatedAt()));
			rawResult = result;
		} else {
			log.error(String.format("更新个人信息失败"));
			this.error = ApiErrorResult.getError(22);
		}
	}
}
