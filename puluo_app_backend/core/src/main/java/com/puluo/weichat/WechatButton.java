package com.puluo.weichat;

import java.util.List;

import com.puluo.util.HasJSON;

public class WechatButton extends HasJSON{
	public String buttonType;
	public String  name;
	public String  key;
	public String  url;
	public List<WechatButton> sub_button;
	public WechatButton(String buttonType, String name, String key, String url,
			List<WechatButton> sub_button) {
		super();
		this.buttonType = buttonType;
		this.name = name;
		this.key = key;
		this.url = url;
		this.sub_button = sub_button;
	}
	
}
