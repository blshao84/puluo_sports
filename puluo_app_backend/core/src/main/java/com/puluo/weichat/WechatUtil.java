package com.puluo.weichat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.puluo.api.result.wechat.WechatArticleMessage;
import com.puluo.config.Configurations;
import com.puluo.entity.PuluoEvent;
import com.puluo.entity.PuluoEventInfo;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;
import com.puluo.util.Strs;

/**
 * 微信通用接口工具类
 * 
 * @author caspar.chen
 * @version 1.0
 * 
 */
public class WechatUtil {

	public static Logger log = Logger.getLogger(WechatUtil.class);

	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public final static String CREATE_BUTTON = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	public final static String GET_MEDIA = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	public final static String GET_MEDIA_LIST = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	public final static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	/**
	 * 获取access_token对象
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return AccessToken对象
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = ACCESS_TOKEN.replace("APPID", appid).replace(
				"APPSECRET", appsecret);
		JSONObject jsonObject = httpsJsonRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				// System.out.println("获取token失败 errcode:" +
				// jsonObject.getInt("errcode")
				// + "，errmsg:" + jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	public static WechatUserInfo getUserInfo(String token,String openid) {
		String requestUrl = GET_USER_INFO.replace("ACCESS_TOKEN", token).replace("OPENID", openid);
		JSONObject jsonObject = httpsJsonRequest(requestUrl, "GET",null);
		Gson gson = new Gson();
		try {
			return gson.fromJson(jsonObject.toString(),
					WechatUserInfo.class);
		} catch (JsonSyntaxException e) {
			log.error("unable to parse :" + jsonObject.toString());
			return null;
		}

	}
	
	public static WechatTextMediaListResult getTextMediaList(String token) {
		return getTextMediaList(token, 0, 20);
	}

	public static WechatTextMediaListResult getTextMediaList(String token,
			int offset, int count) {
		String inputs = String.format(
				"{\"type\":\"%s\",\"offset\":%s,\"count\":%s}", "news", offset,
				count);
		String requestUrl = GET_MEDIA_LIST.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpsJsonRequest(requestUrl, "POST", inputs);
		Gson gson = new Gson();
		try {
			return gson.fromJson(jsonObject.toString(),
					WechatTextMediaListResult.class);
		} catch (JsonSyntaxException e) {
			log.error("unable to parse :" + jsonObject.toString());
			return null;
		}

	}

	public static WechatImageMediaListResult getImageMediaList(String token,
			String type) {
		return getImageMediaList(token, type, 0, 20);
	}

	public static WechatImageMediaListResult getImageMediaList(String token,
			String type, int offset, int count) {
		String inputs = String.format(
				"{\"type\":\"%s\",\"offset\":%s,\"count\":%s}", type, offset,
				count);
		String requestUrl = GET_MEDIA_LIST.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpsJsonRequest(requestUrl, "POST", inputs);
		Gson gson = new Gson();
		try {
			return gson.fromJson(jsonObject.toString(),
					WechatImageMediaListResult.class);
		} catch (JsonSyntaxException e) {
			log.error("unable to parse :" + jsonObject.toString());
			return null;
		}

	}

	public static WechatPermMediaItemResult getTextMedia(String token,
			String mediaID) {
		JSONObject jsonObject = getMedia(token, mediaID);
		Gson gson = new Gson();
		try {
			return gson.fromJson(jsonObject.toString(),
					WechatPermMediaItemResult.class);
		} catch (JsonSyntaxException e) {
			log.error("unable to parse :" + jsonObject.toString());
			return null;
		}
	}

	public static byte[] getImageMedia(String token, String mediaID) {
		String inputs = String.format("{\"media_id\":\"%s\"}", mediaID);
		String requestUrl = GET_MEDIA.replace("ACCESS_TOKEN", token);
		return httpsByteArrayRequest(requestUrl, "POST", inputs);
	}

	public static boolean createButton(String token, String buttons) {

		String requestUrl = CREATE_BUTTON.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = httpsJsonRequest(requestUrl, "POST", buttons);
		System.out.println(jsonObject);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				return (jsonObject.getInt("errcode") == 0);
			} catch (JSONException e) {
				return false;
			}
		}
		return false;
	}

	public static JSONObject httpsJsonRequest(String requestUrl,
			String requestMethod, String outputStr) {
		return httpsRequest(requestUrl, requestMethod, outputStr,
				new WechatHttpJSONPostProcessor());
	}

	public static byte[] httpsByteArrayRequest(String requestUrl,
			String requestMethod, String outputStr) {
		return httpsRequest(requestUrl, requestMethod, outputStr,
				new WechatHttpByteArrayPostProcessor());
	}

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static <T> T httpsRequest(String requestUrl, String requestMethod,
			String outputStr, WechatHttpPostProcessor<T> postProcess) {
		T result = null;
		log.debug(String.format(
				"准备发送http request.\n url=%s\n method=%s\n output=%s\n",
				requestUrl, requestMethod, outputStr));
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
					.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)) {
				// System.out.println("与微信建立http连接");
				httpUrlConn.connect();
			}
			// 当有数据需要提交时
			if (null != outputStr) {
				// System.out.println("向微信发送数据");
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			InputStream inputStream = httpUrlConn.getInputStream();
			result = postProcess.convert(inputStream);
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();

		} catch (ConnectException ce) {
			log.error("server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:" + e);
		}
		return result;
	}

	/**
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
	 * 
	 * @param createTime
	 *            消息创建时间
	 * @return String
	 */
	public static String formatTime(String createTime) {
		// 将微信传入的CreateTime转换成long类型，再乘以1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(msgCreateTime));
	}

	private static JSONObject getMedia(String token, String mediaID) {
		String inputs = String.format("{\"media_id\":\"%s\"}", mediaID);
		String requestUrl = GET_MEDIA.replace("ACCESS_TOKEN", token);
		return httpsJsonRequest(requestUrl, "POST", inputs);
	}

	public static WechatArticleMessage createArticleMessageFromEvent(
			PuluoEvent event, String user_uuid) {
		PuluoEventInfo info = event.eventInfo();
		String name = info.name();
		String desc = info.description();
		String img = null;
		if (info.poster() != null && !info.poster().isEmpty()) {
			img = info.poster().get(0).imageURL();
		} else {
			img = Configurations.imgHttpLink("empty.jpeg");
		}
		String page_url = Strs.join("http://",Configurations.webServer(),
				"/single_event?uuid=", event.eventUUID(), "&user_uuid=",
				user_uuid);
		return new WechatArticleMessage(name, desc, img, page_url, false);
	}

	public static void main(String[] args) {

		// String token = PuluoWechatTokenCache.token();
		/*
		 * WechatPermMediaItemResult res2 = WechatUtil.getTextMedia(token,
		 * Configurations.wechatButtonInfo1List[0]); System.out.println(res2);
		 */
		// WechatTextMediaListResult res = getTextMediaList(token);

		/*
		 * WechatTextMediaListResult res = getTextMediaList(token);
		 * System.out.println(res); for (WechatNewsItem item : res.item) {
		 * System.out.println(item.media_id + ":\n"); for (WechatNewsContentItem
		 * ci : item.content.news_item) { System.out.println("\t" + ci.url); } }
		 */
		// System.out.println(getTextMedia(token,Configurations.wechatButtonInfo2List[0]));
	}

}

class WechatHttpJSONPostProcessor implements
		WechatHttpPostProcessor<JSONObject> {
	public static Log log = LogFactory
			.getLog(WechatHttpJSONPostProcessor.class);

	@Override
	public JSONObject convert(InputStream inputStream) throws Exception {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		bufferedReader.close();
		inputStreamReader.close();
		log.debug(String.format("接收到微信返回的结果:%s", buffer.toString()));
		jsonObject = JSONObject.fromObject(buffer.toString());
		log.debug(String.format("将结果转化为json格式:%s", jsonObject.toString()));
		return jsonObject;
	}

}

class WechatHttpByteArrayPostProcessor implements
		WechatHttpPostProcessor<byte[]> {
	@Override
	public byte[] convert(InputStream inputStream) throws Exception {
		return ByteStreams.toByteArray(inputStream);
	}

}

interface WechatHttpPostProcessor<T> {
	public T convert(InputStream stream) throws Exception;
}