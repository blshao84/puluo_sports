package com.puluo.api.result;

import com.puluo.util.HasJSON;


public class UserProfileResult extends HasJSON{
	public String uuid;
	public UserPublicProfileResult public_info;
	public UserPrivateProfileResult private_info;
	public long created_at;
	public long updated_at;

	public UserProfileResult(String uuid, UserPublicProfileResult public_info,
			UserPrivateProfileResult private_info, long created_at,
			long updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
		this.private_info = private_info;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public UserProfileResult(String uuid, String first_name, String last_name, 
			String thumbnail, String large_image, String saying, int likes, 
			boolean banned, int following, boolean is_coach, String email, 
			String sex, String birthday, String occupation, String country, 
			String state, String city, String zip, long created_at,
			long updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = new UserPublicProfileResult(first_name, last_name,
				thumbnail, large_image, saying, likes, banned, following, is_coach);
		this.private_info = new UserPrivateProfileResult(email, sex, birthday,
				occupation, country, state, city, zip);
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public static UserProfileResult dummy() {
		return new UserProfileResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				UserPublicProfileResult.dummy(),
				UserPrivateProfileResult.dummy(), 
				1427007059034L,1427007059034L);
	}
}


class UserPublicProfileResult {
	public String first_name;
	public String last_name;
	public String thumbnail;
	public String large_image;
	public String saying;
	public int likes;
	public boolean banned;
	public int following;
	public boolean is_coach;

	public UserPublicProfileResult(String first_name, String last_name,
			String thumbnail, String large_image, String saying, int likes,
			boolean banned, int following, boolean is_coach) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.thumbnail = thumbnail;
		this.large_image = large_image;
		this.saying = saying;
		this.likes = likes;
		this.banned = banned;
		this.following = following;
		this.is_coach = is_coach;
	}

	public static UserPublicProfileResult dummy() {

		return new UserPublicProfileResult("Tracy", "Boyd",
				"http://upyun/puluo/userThumb.jpg!200",
				"http://upyun/puluo/userThumb.jpg",
				"I’ve got an app for that.", 2, false, 1, false);
	}
}


class UserPrivateProfileResult {
	public String email;
	public String sex;
	public String birthday;
	public String occupation;
	public String country;
	public String state;
	public String city;
	public String zip;

	public UserPrivateProfileResult(String email, String sex, String birthday,
			String occupation, String country, String state, String city,
			String zip) {
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

	public static UserPrivateProfileResult dummy() {

		return new UserPrivateProfileResult("tracey.boyd@kotebo.com", "m",
				"1984-09-12", "Internet Plumber", "USA", "Washington",
				"Seattle", "234234");
	}
}