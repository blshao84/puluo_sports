package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.dao.impl.PuluoUserDaoImpl;
import com.puluo.entity.impl.PuluoUserImpl;


public class UserProfileUpdateAPI extends
		PuluoAPI<PuluoDSI, UserProfileUpdateResult> {
	public String uuid; //added by Xuyang
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
		PuluoUserDaoImpl userdao = new PuluoUserDaoImpl();
		PuluoUserImpl curuser = new PuluoUserImpl();
		PuluoUserImpl upduser = new PuluoUserImpl();
		
		curuser = (PuluoUserImpl) userdao.getByUUID(uuid);
		upduser = (PuluoUserImpl) userdao.updateProfile(curuser, first_name, last_name, thumbnail, large_image, 
				saying, email, sex, birthday, country, state, city, zip);
		UserProfileUpdateResult result = new UserProfileUpdateResult(upduser.idUser(), upduser.firstName(),
				upduser.lastName(), upduser.thumbnail(), upduser.largeImage(), upduser.saying(), upduser.email(),
				String.valueOf(upduser.sex()), upduser.birthday().toString(), upduser.occupation(),upduser.country(),
				upduser.state(), upduser.city(), upduser.zip(), upduser.create().toString(),upduser.update().toString());
		rawResult = result;
	}
}
