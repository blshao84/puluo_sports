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
import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatButtonType;
import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatPermMediaItemResult;
import com.puluo.weichat.WechatUtil;

public class WechatButtonAPI {
	private Map<String, String> params;
	private WechatButtonType buttonType;

	public WechatButtonAPI(Map<String, String> params) throws Exception {
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
		return new WechatTextMessage("<a href=\"www.baidu.com\">下载App</a>");
	}

	private WechatMessage createCustomerServiceMessage() {
		return new WechatTextMessage("客服电话：400-19840801");
	}

	private WechatMessage createHistOrders() {
		// TODO Auto-generated method stub
		return new WechatTextMessage("coming soon");
	}

	private WechatMessage createRegisterMessage() {
		// TODO Auto-generated method stub
		return new WechatTextMessage("coming soon");
	}

	private WechatMessage createHottestEvent() {
		// TODO Auto-generated method stub
		return new WechatTextMessage("coming soon");
	}

	private WechatMessage createCurriculum() {
		
		return new WechatImageMessage(Configurations.wechatCurriculum);
	}

	private WechatMessage createInfo(String[] mediaList) {
		List<WechatNewsContentItem> items = new ArrayList<WechatNewsContentItem>();
		List<WechatArticleMessage> articles = new ArrayList<WechatArticleMessage>();
		String token = PuluoWechatTokenCache.token();
		for (int i = 0; i < mediaList.length; i++) {
			WechatPermMediaItemResult res2 = WechatUtil.getTextMedia(token,mediaList[i]);
			for(WechatNewsContentItem item:res2.news_item){
				items.add(item);
			}
		}
		for(WechatNewsContentItem i:items){
			articles.add(new WechatArticleMessage(i));
		}
		return new WechatNewsMessage(articles);
	}

}
