package com.puluo.result.user;

import com.puluo.util.HasJSON;


public class UserProfileResult extends HasJSON{
	public String uuid;
	public UserPublicProfileResult public_info;
	public UserPrivateProfileResult private_info;
	public long created_at;
	public long updated_at;

	public UserProfileResult(String uuid, UserPublicProfileResult public_info,
			UserPrivateProfileResult private_info, long created_at,
			long updated_at) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
		this.private_info = private_info;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public static UserProfileResult dummy() {
		return new UserProfileResult("de305d54-75b4-431b-adb2-eb6b9e546013",
				UserPublicProfileResult.dummy(),
				UserPrivateProfileResult.dummy(), 
				1427007059034L,1427007059034L);
	}
}