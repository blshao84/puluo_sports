package com.puluo.weichat;

import java.util.List;

public class WechatNewsItem extends WechatMediaItem {
	public WechatNewsContent content;

	public WechatNewsItem(){
		super();
	}
	
	public WechatNewsItem(String id, String time) {
		super(id, time);
	}

	public WechatNewsItem(String id, String time, WechatNewsContent content) {
		super(id, time);
		this.content = content;
	}
	
	

	public WechatNewsContent getContent() {
		return content;
	}

	public void setContent(WechatNewsContent content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "WechatNewsItem [content=" + content + ", media_id=" + media_id
				+ ", update_time=" + update_time + "]";
	}
	
	

}

class WechatNewsContent {
	public List<WechatNewsContentItem> news_item;

	
	public WechatNewsContent() {}


	public WechatNewsContent(List<WechatNewsContentItem> news_item) {
		super();
		this.news_item = news_item;
	}
	

	public List<WechatNewsContentItem> getNews_item() {
		return news_item;
	}


	public void setNews_item(List<WechatNewsContentItem> news_item) {
		this.news_item = news_item;
	}


	@Override
	public String toString() {
		return "WechatNewsContent [news_item=" + news_item + "]";
	}
	

}

