package com.puluo.api.result;


public class SingleFriendRequestResult {
	public final String request_uuid;
	public final String from_user_uuid;
	public final String to_user_uuid;
	public final long created_at;
	public final long updated_at;
	
	public SingleFriendRequestResult(String request_uuid, String from_user_uuid,
			String to_user_uuid, long created_at, long updated_at) {
		super();
		this.request_uuid = request_uuid;
		this.from_user_uuid = from_user_uuid;
		this.to_user_uuid = to_user_uuid;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	
}
