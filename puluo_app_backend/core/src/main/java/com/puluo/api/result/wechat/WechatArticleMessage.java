package com.puluo.api.result.wechat;

public class WechatArticleMessage extends WechatMessage{
	public final String title;
	public final String description;
	public final String image_url;
	public final String page_url;
	
	public static WechatArticleMessage error = 
			new WechatArticleMessage("","","","",true);
			
	
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
