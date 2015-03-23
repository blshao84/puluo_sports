package com.puluo.api.service;

import com.puluo.api.auth.UserRegistrationAPI;
import com.puluo.api.result.ApiErrorResult;
import com.puluo.api.result.wechat.WechatMessage;
import com.puluo.api.result.wechat.WechatTextMessage;
import com.puluo.dao.PuluoDSI;
import com.puluo.dao.impl.DaoApi;
import com.puluo.entity.PuluoWechatBinding;
import com.puluo.util.Encrypt;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

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

	public WechatMessage process() {
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
						PuluoSMSType.UserRegistration, content);
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
				msg = new WechatTextMessage("抱歉，您的电话号码不正确，我们无法为您发送验证码");
			}
			break;
		case 1:
			String password = Encrypt.sha256(content);
			UserRegistrationAPI api = new UserRegistrationAPI(binding.mobile(),
					password, content);
			api.execute();
			if (api.error == null
					|| api.error.equals(ApiErrorResult.getError(5))) {
				// if successfully create a new user or user has already in the
				// system
				dsi.wechatBindingDao().updateBinding(from_user, 2);
				msg = new WechatTextMessage("您已经成功将手机与微信绑定");
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
			msg = new WechatTextMessage("您已经成功将手机与微信绑定");
			break;
		default:
			dsi.wechatBindingDao().updateBinding(from_user, 0);
			msg = new WechatTextMessage("抱歉，请您再次回复手机已开始绑定");

		}
		return msg;
	}

	private WechatMessage processBindingMessage(PuluoWechatBinding binding) {
		// TODO Auto-generated method stub
		return new WechatTextMessage(Strs.join(
				"Hi, puluo user, I'm puluo echo:", content));
	}

	private WechatMessage processNonBindingMessage() {
		if (content.equals("bd")) {
			dsi.wechatBindingDao().saveBinding("", from_user);
			return new WechatTextMessage("请您微信回复手机号已开始绑定");
		} else
			return new WechatTextMessage(Strs.join("Hi, I'm puluo echo:",
					content));
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