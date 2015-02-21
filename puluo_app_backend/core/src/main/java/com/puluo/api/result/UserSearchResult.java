package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class UserSearchResult extends HasJSON {
	public List<UserSearchResultDetail> details;
	
	public UserSearchResult(List<UserSearchResultDetail> details) {
		super();
		this.details = details;
	}
	
	public static UserSearchResult dummy() {
		List<UserSearchResultDetail> details = new ArrayList<UserSearchResultDetail>();
		details.add(new UserSearchResultDetail("de305d54-75b4-431b-adb2-eb6b9e546013",
				new UserPublicSearchResult("baolins", "Boyd",
						"tracey.boyd@kotebo.com", "123456789000")));
		details.add(new UserSearchResultDetail(
				"ze2345d54-75b4-3234-adb2-ajfs230948jsdf",
				new UserPublicSearchResult("baolins", "Shao", "blshao@qq.com",
						"18646655333")));
		return new UserSearchResult(details);
	}
}

class UserSearchResultDetail {
	public String uuid;
	public UserPublicSearchResult public_info;
	
	public UserSearchResultDetail(String uuid, UserPublicSearchResult public_info) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
	}
	
	public static UserSearchResultDetail dummy() {
		// TODO
		return null;
	}
}

class UserPublicSearchResult {
	public String first_name;
	public String last_name;
	public String email;
	public String mobile;
	
	public UserPublicSearchResult(String first_name, String last_name,
			String email, String mobile) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.mobile = mobile;
	}
	
	public static UserPublicSearchResult dummy() {
		// TODO
		return null;
	}
}