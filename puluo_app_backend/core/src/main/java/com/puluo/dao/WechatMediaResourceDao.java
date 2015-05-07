package com.puluo.dao;

import java.util.List;

import com.puluo.entity.WechatMediaResource;

public interface WechatMediaResourceDao {
	public boolean createTable();

	public boolean saveMediaResource(String mediaID, String mediaName,
			String mediaType, String mediaLink, String mediaItemTitle,
			String medianNewsID);

	public WechatMediaResource getResourceByMediaID(String mediaID);

	public List<WechatMediaResource> getResourceByNewsID(String mediaNewID);

	boolean updateMediaResource(String mediaID, String mediaName,
			String mediaType, String mediaLink, String mediaItemTitle,
			String medianNewsID);

	boolean upsertMediaResource(String mediaID, String mediaName,
			String mediaType, String mediaLink, String mediaItemTitle,
			String medianNewsID);
}
