package com.puluo.result.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.result.message.RequestFriendResult;

public class UserPrivateProfileResult {
	public String email;
	public String sex;
	public String birthday;
	public String occupation;
	public String country;
	public String state;
	public String city;
	public String zip;
	public List<RequestFriendResult> pending;

	public UserPrivateProfileResult(String email, String sex, String birthday,
			String occupation, String country, String state, String city,
			String zip, List<RequestFriendResult> pending) {
		super();
		this.email = email;
		this.sex = sex;
		this.birthday = birthday;
		this.occupation = occupation;
		this.country = country;
		this.state = state;
		this.city = city;
		this.zip = zip;
		this.pending = pending;
	}
	
	public static UserPrivateProfileResult empty() {
		return new UserPrivateProfileResult("","","","","","","","",new ArrayList<RequestFriendResult>());
	}

	public static UserPrivateProfileResult dummy() {
		List<RequestFriendResult> pending = new ArrayList<RequestFriendResult>();
		pending.add(RequestFriendResult.dummy());
		return new UserPrivateProfileResult("tracey.boyd@kotebo.com", "m",
				"1984-09-12", "Internet Plumber", "USA", "Washington",
				"Seattle", "234234", pending);
	}
}