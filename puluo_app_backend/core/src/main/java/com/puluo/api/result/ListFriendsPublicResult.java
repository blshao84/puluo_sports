package com.puluo.api.result;


public class ListFriendsPublicResult {
	public String first_name;
	public String last_name;
	public String email;
	public String mobile;
	
	public ListFriendsPublicResult(String first_name, String last_name,
			String email, String mobile) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.mobile = mobile;
	}
	
	public static ListFriendsPublicResult dummy() {
		// TODO
		return null;
	}
}
