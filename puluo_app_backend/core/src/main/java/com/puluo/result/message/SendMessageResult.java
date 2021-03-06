package com.puluo.result.message;

public class SendMessageResult extends MessageResult {

	public SendMessageResult(String msg_id, String from_user, String to_user,
			String from_user_first_name, String to_user_first_name,
			String from_user_last_name, String to_user_last_name,
			String from_user_thumbnail, String to_user_thumbnail,
			String content, long created_at) {
		super(msg_id, from_user, to_user, from_user_first_name,
				to_user_first_name, from_user_last_name, to_user_last_name,
				from_user_thumbnail, to_user_thumbnail, content, created_at);
	}

	public static SendMessageResult dummy() {
		return new SendMessageResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013",
				"de305d54-75b4-431b-adb2-eb6b9e546013", "Baolin", "Shao",
				"Tracey", "Boydston", "http://upyun.com/puluo/xxxx",
				"http://upyun.com/puluo/xxxx", "hi, this is Tracy!",
				1427007059034L);
	}
}
