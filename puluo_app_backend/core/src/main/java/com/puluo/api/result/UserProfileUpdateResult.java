package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class UserProfileUpdateResult extends HasJSON {

	public String uuid;
	public UserPublicProfileUpdateResult public_info;
	public UserPrivateProfileUpdateResult private_info;
	public long created_at;
	public long updated_at;

	public UserProfileUpdateResult(String uuid, UserPublicProfileUpdateResult public_info,
			UserPrivateProfileUpdateResult private_info, long created_at,
			long updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
		this.private_info = private_info;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public UserProfileUpdateResult(String uuid, String first_name, String last_name, 
			String thumbnail, String large_image, String saying, String email, 
			String sex, String birthday, String occupation, String country, String state, 
			String city, String zip, long created_at, long updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = new UserPublicProfileUpdateResult(first_name, last_name,
				thumbnail, large_image, saying);
		this.private_info = new UserPrivateProfileUpdateResult(email, sex, birthday,
				occupation, country, state, city, zip);
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public static UserProfileUpdateResult dummy() {
		return new UserProfileUpdateResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				UserPublicProfileUpdateResult.dummy(),
				UserPrivateProfileUpdateResult.dummy(), 
				1427007059034L,1427007059034L);
	}
}
	

class UserPublicProfileUpdateResult {
	public String first_name;
    public String last_name;
	public String thumbnail;
	public String large_image;
	public String saying;
	
	public UserPublicProfileUpdateResult(String first_name,
			String last_name, String thumbnail, String large_image,
			String saying) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.thumbnail = thumbnail;
		this.large_image = large_image;
		this.saying = saying;
	}
	
	public static UserPublicProfileUpdateResult dummy() {
		return new UserPublicProfileUpdateResult("Tracey", "Boyd",
				"http://upyun/puluo/userThumb.jpg!200",
				"http://upyun/puluo/userThumb.jpg", "I’ve got an app for that.");
	}
}


class UserPrivateProfileUpdateResult {
	public String email;
	public String sex;
	public String birthday;
	public String occupation;
	public String country;
	public String state;
	public String city;
	public String zip;
	
	public UserPrivateProfileUpdateResult(String email, String sex,
			String birthday, String occupation, String country, String state,
			String city, String zip) {
		super();
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.occupation = occupation;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zip = zip;
	}
	
	public static UserPrivateProfileUpdateResult dummy() {
		return new UserPrivateProfileUpdateResult("tracey.boyd@kotebo.com", "m", "1984-09-12",
				"Internet Plumber", "USA", "Washington", "Seattle", "234234");
	}
}