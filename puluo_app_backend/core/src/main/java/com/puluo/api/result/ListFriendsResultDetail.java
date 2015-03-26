package com.puluo.api.result;


public class ListFriendsResultDetail {
	public String uuid;
	public ListFriendsPublicResult public_info;
	
	public ListFriendsResultDetail(String uuid, ListFriendsPublicResult public_info) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
	}
}
