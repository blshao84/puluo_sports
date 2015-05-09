package com.puluo.result.message;

import java.util.ArrayList;
import java.util.List;

import com.puluo.util.HasJSON;

public class RequestFriendResult extends HasJSON {
	public FriendRequestResult friend_request;

	public RequestFriendResult(FriendRequestResult friend_request) {
		super();
		this.friend_request = friend_request;
	}
	
	public RequestFriendResult(String request_id, String status,
			List<MessageResult> messages, long created_at, long updated_at) {
		this.friend_request = new FriendRequestResult(request_id,status,messages,created_at,updated_at);
	}
	
	public static RequestFriendResult dummy() {
		return new RequestFriendResult(FriendRequestResult.dummy());
	}
}

class FriendRequestResult {
	public String request_id;
	public String status;
	public List<MessageResult> messages;
	public long created_at;
	public long updated_at;
	
	public FriendRequestResult(String request_id, String status,
			List<MessageResult> messages, long created_at, long updated_at) {
		super();
		this.request_id = request_id;
		this.status = status;
		this.messages = messages;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}

	public static FriendRequestResult dummy() {
		List<MessageResult> messages = new ArrayList<MessageResult>();
		messages.add(new MessageResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"Baolin","Shao",
				"Tracey","Boydston",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", 1427007059034L));
		return new FriendRequestResult("", "pending", messages,
				1427007059034L,1427007059034L);
	}
}

/* class RequestMessagesResult {
	public String msg_id;
	public String from_user;
	public String to_user;
	public String from_user_thumbnail;
	public String to_user_thumbnail;
	public String content;
	public String approval;
	public String created_at;
	
	public RequestMessagesResult(String msg_id, String from_user, String to_user,
			String from_user_thumbnail, String to_user_thumbnail,
			String content, String approval, String created_at) {
		super();
		this.msg_id = msg_id;
		this.from_user = from_user;
		this.to_user = to_user;
		this.from_user_thumbnail = from_user_thumbnail;
		this.to_user_thumbnail = to_user_thumbnail;
		this.content = content;
		this.approval = approval;
		this.created_at = created_at;
	}
	
	public static List<RequestMessagesResult> dummy() {
		List<RequestMessagesResult> result = new ArrayList<RequestMessagesResult>();
		result.add(new RequestMessagesResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", "pending", "2012-01-01 12:00:00"));
		return result;
	}
} */