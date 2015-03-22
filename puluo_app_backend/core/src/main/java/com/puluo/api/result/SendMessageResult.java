package com.puluo.api.result;

public class SendMessageResult extends MessageResult {
	/* public String msg_id;
	public String from_user;
	public String to_user;
	public String from_user_thumbnail;
	public String to_user_thumbnail;
	public String content;
	public String created_at;
	
	public SendMessageResult(String msg_id, String from_user, String to_user,
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
	} */

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
