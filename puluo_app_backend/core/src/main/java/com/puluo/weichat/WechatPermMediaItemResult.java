package com.puluo.weichat;

import java.util.List;


public class WechatPermMediaItemResult {
	public List<WechatNewsContentItem> news_item;

	public WechatPermMediaItemResult(List<WechatNewsContentItem> news_item) {
		super();
		this.news_item = news_item;
	}
	
	public WechatPermMediaItemResult() {}
	
	public List<WechatNewsContentItem> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<WechatNewsContentItem> news_item) {
		this.news_item = news_item;
	}

	@Override
	public String toString() {
		return "WechatPermMediaItemResult [news_item=" + news_item + "]";
	}
	
	
}

