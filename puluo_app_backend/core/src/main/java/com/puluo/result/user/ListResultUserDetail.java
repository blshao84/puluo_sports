package com.puluo.result.user;


public class ListResultUserDetail {
	public String uuid;
	public ListUserPublicInfoResult public_info;
	
	public ListResultUserDetail(String uuid, ListUserPublicInfoResult public_info) {
		super();
		this.uuid = uuid;
		this.public_info = public_info;
	}
}
