package com.puluo.api.result;

import com.puluo.util.HasJSON;

public class MessageSummaryResult extends HasJSON{
	public final String user_first_name;
	public final String user_last_name;
	public final String user_uuid;
	public final String user_thumbnail;
	public final long last_message_created_at;
	public final String last_message_uuid;
	
	public MessageSummaryResult(String user_first_name, String user_last_name,
			String user_uuid, String user_thumbnail,
			long last_message_created_at, String last_message_uuid) {
		super();
		this.user_first_name = user_first_name;
		this.user_last_name = user_last_name;
		this.user_uuid = user_uuid;
		this.user_thumbnail = user_thumbnail;
		this.last_message_created_at = last_message_created_at;
		this.last_message_uuid = last_message_uuid;
	}
	
	
}
