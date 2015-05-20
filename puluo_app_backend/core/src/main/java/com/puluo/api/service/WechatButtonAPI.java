package com.puluo.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.puluo.api.result.wechat.WechatArticleMessage;
import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.result.wechat.WechatNewsMessage;
import com.puluo.api.result.wechat.WechatTextMessage;
import com.puluo.config.Configurations;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoPaymentOrder;
import com.puluo.entity.PuluoUser;
import com.puluo.enumeration.WechatButtonType;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;
import com.puluo.util.TimeUtils;
import com.puluo.weichat.PuluoWechatTokenCache;
import com.puluo.weichat.WechatNewsContentItem;
import com.puluo.weichat.WechatPermMediaItemResult;
import com.puluo.weichat.WechatUtil;

public class WechatButtonAPI extends WechatAPI{
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
		case PROMOTION:
			return createPromotion();
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

	private WechatMessage createPromotion() {
		String openId = params.get("FromUserName");
		PuluoUser user = getUserFromOpenID(openId);
		if(user==null){
			return new WechatTextMessage("您还没有注册哦！点击‘我的普罗’一步完成注册!"
					+ "然后向朋友发送注册邀请，您就可以获得一次免费体验普罗团体课的机会");
		} else {
			String url = Strs.join(
					"http://www.puluosports.com/promotion?uuid=",
					user.userUUID());
			return new WechatNewsMessage(
					new WechatArticleMessage(
							"普罗运动开课啦！",
							"点击进入页面，分享到朋友圈，然后拉上小伙伴一起动起来吧！"
									+ "这里有人鱼线MM、马甲线GG以及颜值爆表的教练，这里让运动变的不再枯燥！"
									+ "最重要的是只要您和您的小伙伴注册，所有活动都由小普罗给您买单哦！",
							"http://img.puluosports.com/86765fca-4c76-4110-9f45-f3dfe671a0da.png",
							url, false));
		}
		
	}

	private WechatMessage createDownloadLink() {
		// <a href=\"www.baidu.com\">下载App</a>
		return new WechatTextMessage("敬请期待");
	}

	private WechatMessage createCustomerServiceMessage() {
		return new WechatTextMessage("地址：尚都SOHO西塔三层1308室\n客服电话：010-59003866");
	}

	private WechatMessage createHistOrders() {
		String openId = params.get("FromUserName");
		PuluoUser user = getUserFromOpenID(openId);
		List<PuluoPaymentOrder> orders = DaoApi.getInstance().paymentDao().getPaidOrdersByUserUUID(user.userUUID(), 3);
		StringBuilder history = new StringBuilder();
		PuluoEvent event;
		if (orders.size()==0) {
			history.append("您最近的订单信息不存在。");
		} else {
			history.append("您最近的" + orders.size() + "个订单是：");
			boolean isFirst = true;
			for (PuluoPaymentOrder order: orders) {
				event = DaoApi.getInstance().eventDao().getEventByUUID(order.eventId());
				history.append(isFirst ? "": "；");
				isFirst = false;
				history.append(TimeUtils.formatDate(event.eventTime()).substring(0, 16)
						+ "，" + event.eventInfo().name()
						+ "，" + event.eventLocation().address());
			}
			history.append("。");
		}
		return new WechatTextMessage(history.toString());
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
		String openId = params.get("FromUserName");
		PuluoUser user = getUserFromOpenID(openId);
		String user_uuid;
		if(user==null) user_uuid = ""; else user_uuid=user.userUUID();
		List<PuluoEvent> recommendations = DaoApi.getInstance().eventDao()
				.findPopularEvent(0);
		if (recommendations.size() > 0) {
			List<WechatArticleMessage> articles = new ArrayList<WechatArticleMessage>();
			for (PuluoEvent e : recommendations) {
				articles.add(WechatUtil.createArticleMessageFromEvent(e,user_uuid));
			}
			return new WechatNewsMessage(articles);
		} else
			return new WechatTextMessage("精彩课程，敬请期待！");
	}

	private WechatMessage createCurriculum() {
		return new WechatTextMessage("精彩课程，敬请期待！");
		//return new WechatImageMessage(Configurations.wechatCurriculum);
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
