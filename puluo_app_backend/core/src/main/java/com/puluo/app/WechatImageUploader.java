package com.puluo.app;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.puluo.api.result.ImageUploadServiceResult;
import com.puluo.dao.WechatMediaResourceDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.service.PuluoService;
import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatImageMediaListResult;
import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatNewsItem;
import com.puluo.weichat.WechatRichMediaItem;
import com.puluo.weichat.WechatTextMediaListResult;
import com.puluo.weichat.WechatUtil;

public class WechatImageUploader {

	public static void main(String[] args) {
		saveWechatImages();

	}

	public static void saveWechatImages() {
		String token = PuluoWechatTokenCache.token();
		WechatTextMediaListResult res1 = WechatUtil.getTextMediaList(token);
		Map<String, String> imageIdToNewsId = new HashMap<String, String>();
		Map<String, String> imageIdToItemTitle = new HashMap<String, String>();
		for (WechatNewsItem item : res1.item) {
			System.out.println(item.media_id + ":\n");
			for (WechatNewsContentItem ci : item.content.news_item) {
				imageIdToNewsId.put(ci.thumb_media_id, item.media_id);
				imageIdToItemTitle.put(ci.thumb_media_id, ci.title);
				System.out.println(ci.thumb_media_id + "->" + item.media_id);
				System.out.println(ci.thumb_media_id + "->" + ci.title);
				WechatMediaResourceDao dao = DaoApi.getInstance()
						.wechatMediaResourceDao();
				if(dao.getResourceByMediaID(ci.thumb_media_id)==null){
					doSaveImage(token,ci.thumb_media_id,UUID.randomUUID().toString(),ci.title,item.media_id);
				} else {
					System.out.println("image is already saved, ignore this time ...");
				}
			}
		}
		WechatImageMediaListResult res = WechatUtil.getImageMediaList(token, "image");
		System.out.println(res);
		WechatRichMediaItem item;
		for (int i = 0; i < res.item.size(); i++) {
			item = res.item.get(i);
			// Assume there's no duplicate image ...
			String name = item.name;
			String imageId = item.media_id;
			String newsId = null;
			String itemTitle = null;
			if (imageIdToNewsId.containsKey(imageId)) {
				newsId = imageIdToNewsId.get(imageId);
			} else {
				newsId = "";
			}
			if (imageIdToItemTitle.containsKey(imageId)) {
				itemTitle = imageIdToItemTitle.get(imageId);
			} else {
				itemTitle = "";
			}
			System.out.println(item.media_id + "," + name + "," + itemTitle
					+ "," + newsId);
			doSaveImage(token,item.media_id,name,itemTitle,newsId);
		}
	}

	private static void doSaveImage(String token, String mediaID, String name,
			String title, String newsID) {
		WechatMediaResourceDao dao = DaoApi.getInstance()
				.wechatMediaResourceDao();
		byte[] blob = WechatUtil.getImageMedia(token, mediaID);
		// ImageUploadServiceResult del = PuluoService.image.deleteImage(name);
		// System.out.println(del);
		ImageUploadServiceResult save = PuluoService.image
				.saveImage(blob, name);
		if (save.status.equals("success")) {
			boolean st = dao.upsertMediaResource(mediaID, name, "image", "",
					title, newsID);
			System.out.println(save + ":" + st);
		}
		System.out.println("done");
	}

}
