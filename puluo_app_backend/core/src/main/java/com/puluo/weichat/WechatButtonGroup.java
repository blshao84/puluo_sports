package com.puluo.weichat;

import java.util.List;

import com.puluo.util.HasJSON;

public class WechatButtonGroup extends HasJSON{
	public List<WechatButton> button;

	public WechatButtonGroup(List<WechatButton> button) {
		super();
		this.button = button;
	}
	
}
