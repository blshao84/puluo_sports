package com.puluo.weichat;

import java.util.List;

public class WechatImageMediaListResult {
	public int total_count;
	public int item_count;
	public List<WechatRichMediaItem> item;
	public WechatImageMediaListResult(int total_count, int item_count,List<WechatRichMediaItem> item) {
		super();
		this.total_count = total_count;
		this.item_count = item_count;
		this.item = item;
	}
	
	public WechatImageMediaListResult() {}


	@Override
	public String toString() {
		return "WechatImageMediaListResult [total_count=" + total_count
				+ ", item_count=" + item_count + ", item=" + item + "]";
	}

	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public int getItem_count() {
		return item_count;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	public List<WechatRichMediaItem> getItem() {
		return item;
	}
	public void setItem(List<WechatRichMediaItem> item) {
		this.item = item;
	}
	
}
