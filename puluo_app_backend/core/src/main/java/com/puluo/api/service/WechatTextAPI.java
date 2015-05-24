package com.puluo.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;

import com.puluo.api.auth.UserRegistrationAPI;
import com.puluo.api.event.EventSearchAPI;
import com.puluo.api.result.wechat.WechatArticleMessage;
import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.result.wechat.WechatNewsMessage;
import com.puluo.api.result.wechat.WechatTextMessage;
import com.puluo.config.Configurations;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoUser;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.entity.impl.PuluoCouponImpl;
import com.puluo.enumeration.CouponType;
import com.puluo.enumeration.EventSortType;
import com.puluo.enumeration.EventStatus;
import com.puluo.enumeration.PuluoSMSType;
import com.puluo.enumeration.SortDirection;
import com.puluo.result.ApiErrorResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.PasswordEncryptionUtil;
import com.puluo.util.Strs;
import com.puluo.weichat.WechatUtil;

public class WechatTextAPI extends WechatAPI{
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

	public WechatMessage process() {
		log.info("process text message");
		if (content.isEmpty()) {
			log.error("微信‘文本消息’请求中没有包含content信息");
			return WechatTextMessage.error;
		} else {
			PuluoWechatBinding binding = dsi.wechatBindingDao().findByOpenId(
					from_user);
			if ((binding == null) || (!binding.verified())) {
				if (binding == null) {
					log.info(String.format("user %s hasn't binded the wechat",
							from_user));
					return processNonBindingMessage();
				} else {
					log.info(String
							.format("user %s hasn't binded the wechat, but requested binding, status=%s",
									from_user, binding.status()));
					return processBinding(binding);
				}
			} else {
				log.info("user has alrady bind");
				return processBindingMessage(binding);
			}
		}
	}

	private WechatMessage processBinding(PuluoWechatBinding binding) {
		WechatMessage msg = null;
		switch (binding.status()) {
		case 0:
			if (isMobile()) {
				SMSServiceAPI api = new SMSServiceAPI(
						PuluoSMSType.UserRegistration, content,false);
				api.execute();
				if (api.error == null) {
					dsi.wechatBindingDao().updateMobile(from_user, content);
					dsi.wechatBindingDao().updateBinding(from_user, 1);
					log.info(String
							.format("successfully sent sms，update status to 1 for mobile=%s, openid=%s",
									from_user, content));
					msg = new WechatTextMessage("已成功发送验证码，请您微信回复收到的验证码来完整绑定");
				} else {
					msg = new WechatTextMessage(api.error.message);
				}
			} else {
				log.error(String.format("mobile %s format is wrong", content));
				msg = new WechatTextMessage("抱歉，您还没有注册，请输入您的电话号码，一键搞定！");
			}
			break;
		case 1:
			String password = PasswordEncryptionUtil.encrypt(content);
			UserRegistrationAPI api = new UserRegistrationAPI(binding.mobile(),
					password, content);
			api.execute();
			if (api.error == null
					|| api.error.equals(ApiErrorResult.getError(5))) {
				// if successfully create a new user or user has already in the
				// system
				String newUserUUID = api.userUUID();
				dsi.wechatBindingDao().updateBinding(from_user, 2);
				dsi.couponDao().insertCoupon(new PuluoCouponImpl(
					      UUID.randomUUID().toString(),
					      CouponType.Deduction,
					      Configurations.registrationAwardAmount,
					      newUserUUID,
					      null,
					      Configurations.puluoLocation.locationId(),
					      DateTime.now().plusDays(14)));
				msg = new WechatTextMessage(
						"您已成功注册并将手机与微信绑定！"
						+ "我们向您的账户中存入了一张普罗团课的免费体验券，"
						+ "立刻点击‘酷炫课程’并注册您感兴趣的课程，然后加入我们吧！");
			} else if (api.error.equals(ApiErrorResult.getError(6))) {
				dsi.wechatBindingDao().updateBinding(from_user, 0);
				msg = new WechatTextMessage("抱歉，请您再次回复手机号已开始绑定");
			} else if (api.error.equals(ApiErrorResult.getError(7))) {

				msg = new WechatTextMessage("抱歉,您输入的验证码有误");
			} else {
				msg = new WechatTextMessage("抱歉,请再次输入您收到的验证码");
			}
			break;
		case 2:
			msg = new WechatTextMessage("您已经成功将手机与微信绑定并且注册为普罗运动的会员，您收到的验证码为您的初始密码！");
			break;
		default:
			dsi.wechatBindingDao().updateBinding(from_user, 0);
			msg = new WechatTextMessage("抱歉，请您再次回复手机已开始绑定");

		}
		return msg;
	}

	private WechatMessage processBindingMessage(PuluoWechatBinding binding) {
		if("bd".equals(content)){
			return new WechatTextMessage("您已经成功注册啦！");
		} else {
			EventSearchAPI api = new EventSearchAPI(
				DateTime.now(),//today 
				null, // don't restrict event_to_date
				content, 
				null, //ignore level
				EventSortType.Price, 
				SortDirection.Asc, 
				0, 0, 0, //ignore location for now
				EventStatus.Open, 
				null,5,0); // any event type
			api.execute();
			List<PuluoEvent> events = api.searchedEvents;
			log.info(String.format("searched %s events from wechat",
					events.size()));
			if(events.size()>0){
				PuluoUser user = getUserFromOpenID(from_user);
				String user_uuid;
				if(user==null) user_uuid=""; else user_uuid=user.userUUID();
				List<WechatArticleMessage> articles = new ArrayList<WechatArticleMessage>();
				for (PuluoEvent e : events) {
					articles.add(WechatUtil.createArticleMessageFromEvent(e,user_uuid));
				}
				return new WechatNewsMessage(articles);
			} else
				return new WechatTextMessage("没有找到您感兴趣的课程");
		}
	}

	private WechatMessage processNonBindingMessage() {
		if (content.equals("bd")) {
			dsi.wechatBindingDao().saveBinding(UUID.randomUUID().toString(), from_user);
			return new WechatTextMessage("请您微信回复手机号，两步即可完成绑定！");
		} else
			return new WechatTextMessage(Strs.join("您还没有注册喔！点击右下角'我的普罗'->'快速注册'，一键完成！"));
	}

	private boolean isMobile() {
		try {
			Long.valueOf(content);
			if (content.length() == 11) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

}
