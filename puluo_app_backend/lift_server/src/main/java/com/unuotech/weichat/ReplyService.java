package com.unuotech.weichat;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * 发送客服消息
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class ReplyService {

	public static Logger log = Logger.getLogger(ReplyService.class);

	private static String CUSTOME_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	
	/**
	 * 发送客服消息
	 * @param obj	消息对象
	 * @return 是否发送成功
	 */
	public static boolean sendCustomerMessage(Object obj,String token) {
		boolean bo = false;
		String url = CUSTOME_URL.replace("ACCESS_TOKEN", token);
		JSONObject json = JSONObject.fromObject(obj);
		System.out.println(json);
		JSONObject jsonObject = WeiChatUtil.httpsRequest(url, "POST", json.toString());
		if (null != jsonObject) {
			if (StringUtil.isNotEmpty(jsonObject.getString("errcode"))
					&& jsonObject.getString("errcode").equals("0")) {
				bo = true;
			}
		}
		return bo;
	}
}
