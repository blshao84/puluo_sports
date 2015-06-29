package com.puluo.app;

import org.joda.time.DateTime;

import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.service.WechatTextAPI;
import com.puluo.util.TimeUtils;

public class FixWechatRegistration {
	public static void main(String[] args) {
		String fromUser= "oNTPZsxLYRbIukPVe610OCb3ff7o";
		WechatTextAPI api = new WechatTextAPI("", fromUser, TimeUtils.formatDate(DateTime.now()), "", "360534");
		WechatMessage msg= api.process();
		System.out.println(msg.toString());
	}
}
