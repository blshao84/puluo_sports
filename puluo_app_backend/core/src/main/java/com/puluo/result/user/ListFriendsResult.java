package com.puluo.result.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;


public class ListFriendsResult extends HasJSON {
	public List<ListFriendsResultDetail> details;
	
	public ListFriendsResult(List<ListFriendsResultDetail> details) {
		super();
		this.details = details;
	}
	
	public static ListFriendsResult dummy() {
		List<ListFriendsResultDetail> details = new ArrayList<ListFriendsResultDetail>();
		details.add(new ListFriendsResultDetail("de305d54-75b4-431b-adb2-eb6b9e546013",
				new ListFriendsPublicResult("baolins", "Boyd",
						"tracey.boyd@kotebo.com", "123456789000","http://upyun.com/tracey.jpg","I am Tracey")));
		details.add(new ListFriendsResultDetail(
				"ze2345d54-75b4-3234-adb2-ajfs230948jsdf",
				new ListFriendsPublicResult("baolins", "Shao", "blshao@qq.com",
						"18646655333","http://upyun.com/baolin.jpg","I am Baolin")));
		return new ListFriendsResult(details);
	}
}
