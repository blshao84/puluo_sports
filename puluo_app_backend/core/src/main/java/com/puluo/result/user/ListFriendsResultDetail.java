package com.puluo.result.user;


public class ListFriendsResultDetail {
	public String uuid;
	public ListFriendsPublicResult public_info;
	
	public ListFriendsResultDetail(String uuid, ListFriendsPublicResult public_info) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
	}
}
