package com.puluo.dao;

import com.puluo.entity.WechatMediaResource;

public interface WechatMediaResourceDao {
	public boolean createTable();
	public boolean saveMediaResource(String mediaID,String mediaName,String mediaType,String mediaLink);
	public WechatMediaResource getResourceByMediaID(String mediaID);
}
