package com.puluo.weichat;


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

