package com.puluo.api.result.wechat;

import com.puluo.config.Configurations;
import com.puluo.dao.WechatMediaResourceDao;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.WechatMediaResource;
import com.puluo.util.Strs;
import com.puluo.weichat.WechatNewsContentItem;

public class WechatArticleMessage extends WechatMessage {
	public final String title;
	public final String description;
	public final String image_url;
	public final String page_url;

	public static WechatArticleMessage error = new WechatArticleMessage("", "",
			"", "", true);

	public WechatArticleMessage(WechatNewsContentItem item) {
		WechatMediaResourceDao dao = DaoApi.getInstance()
				.wechatMediaResourceDao();
		WechatMediaResource res = dao.getResourceByMediaID(item.thumb_media_id);
		this.title = item.title;
		this.description = item.digest;
		this.page_url = item.content_source_url;
		if (res != null) {
			this.image_url = Strs.join(Configurations.imageServer,res.wechatName());
		} else{
			this.image_url = "";
		}
	}

	public WechatArticleMessage(String title, String description,
			String image_url, String page_url, boolean is_error) {
		super();
		this.title = title;
		this.description = description;
		this.image_url = image_url;
		this.page_url = page_url;
		this.is_error = is_error;
	}

}
