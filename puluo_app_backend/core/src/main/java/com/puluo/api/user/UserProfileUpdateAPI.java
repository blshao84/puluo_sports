package com.puluo.api.user;

import com.puluo.api.PuluoAPI;
import com.puluo.api.result.UserProfileUpdateResult;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;

public class UserProfileUpdateAPI extends
		PuluoAPI<PuluoDSI, UserProfileUpdateResult> {
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

	public UserProfileUpdateAPI(String first_name, String last_name,
			String thumbnail, String large_image, String saying, String email,
			String sex, String birthday, String country, String state,
			String city, String zip) {
		this(first_name, last_name, thumbnail, large_image, saying, email, sex,
				birthday, country, state, city, zip, DaoApi.getInstance());
	}

	public UserProfileUpdateAPI(String first_name, String last_name,
			String thumbnail, String large_image, String saying, String email,
			String sex, String birthday, String country, String state,
			String city, String zip, PuluoDSI dsi) {
		this.dsi = dsi;
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
		// TODO Auto-generated method stub

	}

}
