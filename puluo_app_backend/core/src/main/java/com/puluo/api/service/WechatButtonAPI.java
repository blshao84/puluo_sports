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

public class WechatButtonAPI extends WechatAPI {
	public static Log log = LogFactory.getLog(WechatButtonAPI.class);
	private final Map<String, String> params;
	private WechatButtonType buttonType;

	public WechatButtonAPI(Map<String, String> params) throws Exception {
		this.params = params;
		String event = params.get("Event");
		String eventKey = params.get("EventKey");
		if (event.toLowerCase().equals("click")) {
			this.buttonType = WechatButtonType.valueOf(eventKey);
		} else if (event.toLowerCase().equals("subscribe")) {
			this.buttonType = WechatButtonType.SUBSCRIBE;
		} else if (event.toLowerCase().equals("view")) {
			// TODO: should log this for further stat!!
			log.info("user view through wechat button");
		} else if (event.toLowerCase().equals("unsubscribe")) {
			// TODO: should log this for further stat!!
			log.info("user unsubscribe through wechat button");
		} else
			throw new Exception(String.format(
					"Unexpected wechat button request:event=%s,eventKey=%s",
					event, eventKey));
	}

	public WechatMessage process() throws Exception {
		if (buttonType != null) {
			switch (buttonType) {
			case SUBSCRIBE:
				return createIntro();
			case INFO1:
				return createInfo(Configurations.wechatButtonInfo1List);
			case INFO2:
				return createInfo(Configurations.wechatButtonInfo2List);
			case INFO3:
				return createInfo(Configurations.wechatButtonInfo3List);
			case INFO4:
				return createInfo(Configurations.wechatButtonInfo4List);
			case INFO5:
				return createInfo(Configurations.wechatButtonInfo5List);
			case CURRICULUM:
				return createCurriculum();
			case PROMOTION:
				return createPromotion();
			case INTRODUCTION:
				return createIntro();
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
		} else
			return new WechatTextMessage("");
	}

	private WechatMessage createIntro() {
		return new WechatTextMessage("时尚火爆的普罗运动有免费团课咯！！！\n"
				+ "下班后疲惫无聊，除了吃饭压马路没别的好玩去处怎么办？"
				+ "聚会交友想有趣、好玩儿又健康怎么办？一个人找不到伴儿一起健身怎么办？？"
				+ "雾霾天空气不好还想运动怎么办？不想办健身卡又想健身怎么办？"
				+ "那你就来普罗运动吧亲～扔掉枯燥器械、不办卡无年费、各路有趣教练、各式好玩儿健身团课，让你挑花眼！\n\n"
				+ "限时优惠活动：\n" + "点击‘呼唤小伙伴’并向朋友圈转发或推荐给好友，好友通过你的推荐注册成功后，"
				+ "你将获得一次免费上课代金券！多转多送，普罗团课等你来噢！\n" + "电话：010-59003866\n"
				+ "地址：尚都SOHO西塔三层1308室。");
	}

	private WechatMessage createPromotion() {
		String openId = params.get("FromUserName");
		PuluoUser user = getUserFromOpenID(openId);
		if (user == null) {
			return new WechatTextMessage(
					"您还没有注册哦！点击‘注册抢优惠’一步完成注册及绑定微信!"
					+ "之后点击‘呼唤小伙伴’并向朋友圈转发或推荐给好友，好友通过您的推荐注册成功后，"
					+ "您将获得一次免费上课代金券！多转多送，普罗团课等你来噢！\n");
		} else {
			String url = Strs.join("http://", Configurations.webServer(),
					"/promotion?uuid=", user.userUUID());
			return new WechatNewsMessage(
					new WechatArticleMessage(
							"普罗运动转发抢优惠",
							"点击该文章并转发朋友圈邀请好友加入，您将获得一张免费上课优惠劵。",
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
		List<PuluoPaymentOrder> orders = DaoApi.getInstance().paymentDao()
				.getPaidOrdersByUserUUID(user.userUUID(), 3);
		StringBuilder history = new StringBuilder();
		PuluoEvent event;
		if (orders.size() == 0) {
			history.append("您最近的订单信息不存在。");
		} else {
			history.append("您最近的" + orders.size() + "个订单是：");
			boolean isFirst = true;
			for (PuluoPaymentOrder order : orders) {
				event = DaoApi.getInstance().eventDao()
						.getEventByUUID(order.eventId());
				history.append(isFirst ? "" : "；");
				isFirst = false;
				history.append(TimeUtils.formatDate(event.eventTime())
						.substring(0, 16)
						+ "，"
						+ event.eventInfo().name()
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
		if (user == null)
			user_uuid = "";
		else
			user_uuid = user.userUUID();
		List<PuluoEvent> recommendations = DaoApi.getInstance().eventDao()
				.findPopularEvent(0);
		if (recommendations.size() > 0) {
			List<WechatArticleMessage> articles = new ArrayList<WechatArticleMessage>();
			for (PuluoEvent e : recommendations) {
				articles.add(WechatUtil.createArticleMessageFromEvent(e,
						user_uuid));
			}
			return new WechatNewsMessage(articles);
		} else
			return new WechatTextMessage("精彩课程，敬请期待！");
	}

	private WechatMessage createCurriculum() {
		return new WechatTextMessage("精彩课程，敬请期待！");
		// return new WechatImageMessage(Configurations.wechatCurriculum);
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
