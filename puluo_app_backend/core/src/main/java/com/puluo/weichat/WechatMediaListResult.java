package com.puluo.weichat;

import java.util.List;

public class WechatMediaListResult {
	public int total_count;
	public int item_count;
	public List<WechatNewsItem> item;
	public WechatMediaListResult(int total_count, int item_count,List<WechatNewsItem> item) {
		super();
		this.total_count = total_count;
		this.item_count = item_count;
		this.item = item;
	}
	
	public WechatMediaListResult() {}

	@Override
	public String toString() {
		return "WechatMediaListResult [total_count=" + total_count
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
	public List<WechatNewsItem> getItem() {
		return item;
	}
	public void setItem(List<WechatNewsItem> item) {
		this.item = item;
	}
	
}
