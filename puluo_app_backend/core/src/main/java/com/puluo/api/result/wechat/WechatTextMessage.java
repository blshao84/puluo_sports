package com.puluo.api.result.wechat;

public class WechatTextMessage extends WechatMessage {
	public final String content;

	public static final WechatTextMessage error = new WechatTextMessage("",
			true);

	public WechatTextMessage(String content, boolean is_error) {
		super();
		this.content = content;
		this.is_error = is_error;
	}
	
	public WechatTextMessage(String content) {
		super();
		this.content = content;
		this.is_error = false;
	}

	@Override
	public String toString() {
		return "WechatTextMessage [content=" + content + "]";
	}
	
	

}
