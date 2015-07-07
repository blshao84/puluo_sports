package com.puluo.app;

import org.joda.time.DateTime;

import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.service.WechatTextAPI;
import com.puluo.util.TimeUtils;

public class FixWechatRegistration {
	public static void main(String[] args) {
		String fromUser= "oNTPZs9OupjcdKCMrbi_yBTmWaoY";
		WechatTextAPI api = new WechatTextAPI("", fromUser, TimeUtils.formatDate(DateTime.now()), "", "183254");
		WechatMessage msg= api.process();
		System.out.println(msg.toString());
	}
}
