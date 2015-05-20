package com.puluo.api.result.wechat;

import java.util.ArrayList;
import java.util.List;

import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatPermMediaItemResult;

public class WechatNewsMessage extends WechatMessage{
	public final List<WechatArticleMessage> articles;
	public WechatNewsMessage(List<WechatArticleMessage> articles) {
		super();
		this.articles = articles;
	}
	
	public WechatNewsMessage(WechatArticleMessage article) {
		super();
		List<WechatArticleMessage> as = new ArrayList<WechatArticleMessage>();
		as.add(article);
		this.articles = as;
	}
	
	public WechatNewsMessage(WechatPermMediaItemResult result, String mediaID) {
		articles = new ArrayList<WechatArticleMessage>();
		for(WechatNewsContentItem item:result.news_item){
			articles.add(new WechatArticleMessage(item,mediaID));
		}
	}
	
}
