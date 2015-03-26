package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoUserFriendship;
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
						"tracey.boyd@kotebo.com", "123456789000")));
		details.add(new ListFriendsResultDetail(
				"ze2345d54-75b4-3234-adb2-ajfs230948jsdf",
				new ListFriendsPublicResult("baolins", "Shao", "blshao@qq.com",
						"18646655333")));
		return new ListFriendsResult(details);
	}
}

/*class ListFriendsResultDetail {
	public String uuid;
	public ListFriendsPublicResult public_info;
	
	public ListFriendsResultDetail(String uuid, ListFriendsPublicResult public_info) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
	}
}

class ListFriendsPublicResult {
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
}*/
