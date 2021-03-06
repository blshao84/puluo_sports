package com.puluo.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import com.puluo.service.util.JuheSMSResult;
import com.puluo.util.Log;
import com.puluo.util.LogFactory;

public class JuheSMSClient {
	private static final Log LOGGER = LogFactory.getLog(JuheSMSClient.class);

	private final HttpClient client;
	private final String baseURL;

	public JuheSMSClient(HttpClient client, String baseURL) {
		this.client = client;
		this.baseURL = baseURL;
	}

	public JuheSMSResult sendAuthCode(String mobile, String code) {
		String values = String.format("#code#=%s", code);
		return doSend(2041, values, mobile);
	}

	public JuheSMSResult sendConfirmationMessage(String mobile, String time,
			String loc, String eventName) {
		String values = String.format(
				"#time#=%s&#loc#=%s&#name#=%s", time, loc,
				eventName);
		return doSend(2042, values, mobile);
	}
	

	//FIXME: should add a new tempalte 
	public JuheSMSResult sendWarningConfirmationMessage(String mobile) {
		String values = String.format("#company#=普罗体育");
		return doSend(3, values, mobile);
		
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
		return baos.toString("UTF-8");

	}

}