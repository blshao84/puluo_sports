package com.puluo.weichat;

public class WechatRichMediaItem extends WechatMediaItem{
	public String name;
	public WechatRichMediaItem(String media_id,String update_time, String name){
		super(media_id,update_time);
		this.name = name;
	}
	@Override
	public String toString() {
		return "WechatRichMediaItem [name=" + name + ", media_id=" + media_id
				+ ", update_time=" + update_time + "]";
	}
	
}
