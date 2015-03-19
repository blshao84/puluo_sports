package com.puluo.api.result.wechat;

import java.util.List;

public class WechatNewsMessage extends WechatMessage{
	public final List<WechatArticleMessage> articles;

	public WechatNewsMessage(List<WechatArticleMessage> articles) {
		super();
		this.articles = articles;
	}
	
}
