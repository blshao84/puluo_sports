package com.puluo.api.service;

import com.puluo.api.result.wechat.WechatTextMessage;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class WechatTextAPI {
	public static Log log = LogFactory.getLog(WechatTextAPI.class);
	public final String to_user;
	public final String from_user;
	public final String created_at;
	public final String message_type;
	public final String content;

	private final PuluoDSI dsi;

	public WechatTextAPI(String to_user, String from_user, String created_at,
			String message_type, String content) {
		this(to_user, from_user, created_at, message_type, content, DaoApi
				.getInstance());
	}

	public WechatTextAPI(String to_user, String from_user, String created_at,
			String message_type, String content, PuluoDSI dsi) {
		super();
		this.to_user = to_user;
		this.from_user = from_user;
		this.created_at = created_at;
		this.message_type = message_type;
		this.content = content;
		this.dsi = dsi;
	}

	public WechatTextMessage process() {
		if(content.isEmpty()){
			log.error("微信‘文本消息’请求中没有包含content信息");
			return WechatTextMessage.error;
		} else {
			return new WechatTextMessage(content);
		}
	}


}
