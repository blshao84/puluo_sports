package com.puluo.api.result;

public class UserPrivateProfileResult {
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
	
	public static UserPrivateProfileResult empty() {
		return new UserPrivateProfileResult("","","","","","","","");
	}

	public static UserPrivateProfileResult dummy() {

		return new UserPrivateProfileResult("tracey.boyd@kotebo.com", "m",
				"1984-09-12", "Internet Plumber", "USA", "Washington",
				"Seattle", "234234");
	}
}