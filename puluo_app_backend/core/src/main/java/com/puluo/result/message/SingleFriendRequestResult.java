package com.puluo.result.message;


public class SingleFriendRequestResult {
	public final String request_uuid;
	public final String from_user_uuid;
	public final String from_user_first_name;
	public final String from_user_last_name;
	public final String from_user_thumbnail;
	public final String to_user_uuid;
	public final String to_user_first_name;
	public final String to_user_last_name;
	public final String to_user_thumbnail;
	public final long created_at;
	public final long updated_at;
	public SingleFriendRequestResult(String request_uuid,
			String from_user_uuid, String from_user_first_name,
			String from_user_last_name, String from_user_thumbnail,
			String to_user_uuid, String to_user_first_name,
			String to_user_last_name, String to_user_thumbnail,
			long created_at, long updated_at) {
		super();
		this.request_uuid = request_uuid;
		this.from_user_uuid = from_user_uuid;
		this.from_user_first_name = from_user_first_name;
		this.from_user_last_name = from_user_last_name;
		this.from_user_thumbnail = from_user_thumbnail;
		this.to_user_uuid = to_user_uuid;
		this.to_user_first_name = to_user_first_name;
		this.to_user_last_name = to_user_last_name;
		this.to_user_thumbnail = to_user_thumbnail;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	
	
	
}
