package com.puluo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.puluo.service.util.JuheSMSResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

class JuheSMSClient {
	private static final Log LOGGER = LogFactory.getLog(JuheSMSClient.class);

	private  HttpClient client; 
	private  String baseURL;

	public JuheSMSClient(HttpClient client, String baseURL) {
		this.client = client;
		this.baseURL = baseURL;
	}
	
	public JuheSMSResult sendVerificationCode(String mobile, String code) {
		String values = String.format("#code#=%s&#company#=大庆优诺", code);
		return doSend(1, values, mobile);
	}

	private JuheSMSResult doSend(int templateId, String templateValue,
			String mobile) {
		try {
			String url = new StringBuilder(baseURL).append("mobile=")
					.append(mobile).append("&tpl_id=").append(templateId)
					.append("&tpl_value=")
					.append(URLEncoder.encode(templateValue, "utf-8"))
					.toString();
			LOGGER.info(String.format("向短信服务发送内容%s,请求%s", templateValue, url));
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = client.execute(httpGet);
			InputStream data = response.getEntity().getContent();

			String body = inputstream2String(data);
			LOGGER.info(String.format("短信发送结果:%s", body));
			httpGet.releaseConnection();
			return JuheSMSResult.fromJson(body);
		} catch (Exception ex) {
			LOGGER.error("无法将Juhe返回的数据转换为字符串");
			return JuheSMSResult.error;
		}
	}

	private String inputstream2String(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		i = is.read();
		while (i != -1) {
			baos.write(i);
			i = is.read();
		}
		return baos.toString();

	}

}