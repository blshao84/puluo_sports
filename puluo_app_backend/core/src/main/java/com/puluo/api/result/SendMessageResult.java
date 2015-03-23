package com.puluo.api.result;

public class SendMessageResult extends MessageResult {

	public SendMessageResult(String msg_id, String from_user, String to_user,
			String from_user_thumbnail, String to_user_thumbnail,
			String content, long created_at) {
		super(msg_id, from_user, to_user, from_user_thumbnail, to_user_thumbnail,
				content, created_at);
	}

	public static SendMessageResult dummy() {
		return new SendMessageResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"http://upyun.com/puluo/xxxx", "http://upyun.com/puluo/xxxx",
				"hi, this is Tracy!", 1427007059034L);
	}
}
