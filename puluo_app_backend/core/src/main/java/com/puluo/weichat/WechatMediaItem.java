package com.puluo.weichat;

public class WechatMediaItem {
	public String media_id;
	public String update_time;
	
	public WechatMediaItem(String id, String time){
		this.media_id = id;
		this.update_time = time;
	}
	

	public WechatMediaItem() {}


	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}


	@Override
	public String toString() {
		return "WechatMediaItem [media_id=" + media_id + ", update_time="
				+ update_time + "]";
	}
	
	
}
