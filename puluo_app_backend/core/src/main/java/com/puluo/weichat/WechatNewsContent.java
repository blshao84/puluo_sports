package com.puluo.weichat;

import java.util.List;

public class WechatNewsContent {
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

