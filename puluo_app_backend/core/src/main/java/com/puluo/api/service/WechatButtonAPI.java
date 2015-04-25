package com.puluo.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.puluo.api.result.wechat.WechatArticleMessage;
import com.puluo.api.result.wechat.WechatImageMessage;
import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.result.wechat.WechatNewsMessage;
import com.puluo.api.result.wechat.WechatTextMessage;
import com.puluo.config.Configurations;
import com.puluo.enumeration.WechatButtonType;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatPermMediaItemResult;
import com.puluo.weichat.WechatUtil;

public class WechatButtonAPI {
	public static Log log = LogFactory.getLog(WechatButtonAPI.class);
	private final Map<String, String> params;
	private WechatButtonType buttonType;

	public WechatButtonAPI(Map<String, String> params) throws Exception {
		this.params = params;
		String event = params.get("Event");
		String eventKey = params.get("EventKey");
		if (event.equals("CLICK")) {
			this.buttonType = WechatButtonType.valueOf(eventKey);
		} else
			throw new Exception(String.format(
					"Unexpected wechat button request:event=%s,eventKey=%s",
					event, eventKey));
	}

	public WechatMessage process() throws Exception {
		switch (buttonType) {
		case INFO1:
			return createInfo(Configurations.wechatButtonInfo1List);
		case INFO2:
			return createInfo(Configurations.wechatButtonInfo2List);
		case INFO3:
			return createInfo(Configurations.wechatButtonInfo3List);
		case INFO4:
			return createInfo(Configurations.wechatButtonInfo4List);
		case CURRICULUM:
			return createCurriculum();
		case HOTTEST:
			return createHottestEvent();
		case REGISTER:
			return createRegisterMessage();
		case ORDERS:
			return createHistOrders();
		case DOWNLOAD:
			return createDownloadLink();
		case SERVICE:
			return createCustomerServiceMessage();
		default:
			throw new Exception("Unexpected button Type");
		}
	}

	private WechatMessage createDownloadLink() {
		//<a href=\"www.baidu.com\">下载App</a>
		return new WechatTextMessage("敬请期待");
	}

	private WechatMessage createCustomerServiceMessage() {
		return new WechatTextMessage("地址：尚都SOHO西塔三层1308室\n客服电话：13522898646");
	}

	private WechatMessage createHistOrders() {
		// TODO Auto-generated method stub
		return new WechatTextMessage("coming soon");
	}

	private WechatMessage createRegisterMessage() {
		log.info("creating register message");
		String toUser = params.get("ToUserName");
		String fromUser = params.get("FromUserName");
		String time = params.get("CreateTime");
		String type = params.get("MsgType");
		WechatTextAPI textAPI = new WechatTextAPI(toUser, fromUser, time, type,
				"bd");
		log.info(String.format(
				"create WechatTextAPI, toUser=%s, fromUser=%s,time=%s,type=%s",
				toUser, fromUser, time, type));
		WechatMessage res = textAPI.process();
		log.info("registration result:" + res.toString());
		return res;
	}

	private WechatMessage createHottestEvent() {
		// TODO Auto-generated method stub
		return new WechatTextMessage("敬请期待");
	}

	private WechatMessage createCurriculum() {

		return new WechatImageMessage(Configurations.wechatCurriculum);
	}

	private WechatMessage createInfo(String[] mediaList) {
		List<WechatArticleMessage> articles = new ArrayList<WechatArticleMessage>();
		String token = PuluoWechatTokenCache.token();
		for (int i = 0; i < mediaList.length; i++) {
			String newsId = mediaList[i];
			WechatPermMediaItemResult res2 = WechatUtil.getTextMedia(token,
					newsId);
			for (WechatNewsContentItem item : res2.news_item) {
				articles.add(new WechatArticleMessage(item, newsId));
			}
		}
		return new WechatNewsMessage(articles);
	}

}
