package com.puluo.result.user;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class BlacklistUpdateResult extends HasJSON{
	public String uuid;
	public boolean is_success;
	public List<ListResultUserDetail> details;
	
	public BlacklistUpdateResult(String uuid,boolean is_success,
			List<ListResultUserDetail> details) {
		super();
		this.uuid = uuid;
		this.is_success = is_success;
		this.details = details;
	}
	
	public static BlacklistUpdateResult dummy() {
		List<ListResultUserDetail> details = new ArrayList<ListResultUserDetail>();
		details.add(new ListResultUserDetail("de305d54-75b4-431b-adb2-eb6b9e546013",
				new ListUserPublicInfoResult("baolins", "Boyd",
						"tracey.boyd@kotebo.com", "123456789000","http://upyun.com/tracey.jpg","I am Tracey")));
		details.add(new ListResultUserDetail(
				"ze2345d54-75b4-3234-adb2-ajfs230948jsdf",
				new ListUserPublicInfoResult("baolins", "Shao", "blshao@qq.com",
						"18646655333","http://upyun.com/baolin.jpg","I am Baolin")));
		return new BlacklistUpdateResult("ze2345d54-75b4-3234-adb2-ajfs230948jsdf",true,details);
	}
}
