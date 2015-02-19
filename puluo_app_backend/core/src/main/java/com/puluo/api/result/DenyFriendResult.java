package com.puluo.api.result;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class DenyFriendResult extends HasJSON {
	public FriendDenyResult friend_request;

	public DenyFriendResult(FriendDenyResult friend_request) {
		super();
		this.friend_request = friend_request;
	}
	
	public static DenyFriendResult dummy() {
		return new DenyFriendResult(FriendDenyResult.dummy());
	}
}

class FriendDenyResult {
	public String request_id;
	public String status;
	public List<DenyMessagesResult> messages;
	public String created_at;
	public String updated_at;
	
	public FriendDenyResult(String request_id, String status,
			List<DenyMessagesResult> messages, String created_at, String updated_at) {
		super();
		this.request_id = request_id;
		this.status = status;
		this.messages = messages;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public static FriendDenyResult dummy() {
		return new FriendDenyResult("", "denied", DenyMessagesResult.dummy(),
				"2012-01-01 12:00:00", "2012-01-01 12:00:00");
	}
}

class DenyMessagesResult {
	public String msg_id;
	public String from_user;
	public String to_user;
	public String from_user_thumbnail;
	public String to_user_thumbnail;
	public String content;
	public String created_at;
	
	public DenyMessagesResult(String msg_id, String from_user, String to_user,
			String from_user_thumbnail, String to_user_thumbnail,
			String content, String created_at) {
		super();
		this.msg_id = msg_id;
		this.from_user = from_user;
		this.to_user = to_user;
		this.from_user_thumbnail = from_user_thumbnail;
		this.to_user_thumbnail = to_user_thumbnail;
		this.content = content;
		this.created_at = created_at;
	}
	
	public static List<DenyMessagesResult> dummy() {
		List<DenyMessagesResult> result = new ArrayList<DenyMessagesResult>();
		result.add(new DenyMessagesResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", "2012-01-01 12:00:00"));
		result.add(new DenyMessagesResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", "2012-01-01 12:00:00"));
		return result;
	}
}