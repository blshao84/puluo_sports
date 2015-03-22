package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class MessageResult extends HasJSON {
	public String msg_id;
	public String from_user;
	public String to_user;
	public String from_user_thumbnail;
	public String to_user_thumbnail;
	public String content;
	public long created_at;

	public MessageResult(String msg_id, String from_user, String to_user,
			String from_user_thumbnail, String to_user_thumbnail,
			String content, long created_at) {
		super();
		this.msg_id = msg_id;
		this.from_user = from_user;
		this.to_user = to_user;
		this.from_user_thumbnail = from_user_thumbnail;
		this.to_user_thumbnail = to_user_thumbnail;
		this.content = content;
		this.created_at = created_at;
	}
}
