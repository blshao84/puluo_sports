package com.unuotech.payment.example;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unuotech.payment.config.UnionPayConf;
import com.unuotech.payment.unionpay.utils.UnionPayUtils;

/**
 * 名称：支付应答类
 * 功能：servlet方式，建议前台通知和后台通知用两个类实现，后台通知进行商户的数据库等处理,前台通知实现客户浏览器跳转
 * 类属性：
 * 版本：1.0
 * 日期：2011-03-11
 * 作者：中国银联UPOP团队
 * 版权：中国银联
 * 说明：以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。该代码仅供参考。
 * */

public class UnionPayResSampleServLet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6247574514957274823L;

	public void init() {

	}

	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding(UnionPayConf.charset);
		} catch (UnsupportedEncodingException e) {
		}
		
		String[] resArr = new String[UnionPayConf.notifyVo.length]; 
		for(int i=0;i<UnionPayConf.notifyVo.length;i++){
			resArr[i] = request.getParameter(UnionPayConf.notifyVo[i]);
		}
		String signature = request.getParameter(UnionPayConf.signature);
		String signMethod = request.getParameter(UnionPayConf.signMethod);
		
		response.setContentType("text/html;charset="+UnionPayConf.charset);   
		response.setCharacterEncoding(UnionPayConf.charset);
		
		try {
			Boolean signatureCheck = new UnionPayUtils().checkSign(resArr, signMethod, signature);
			response.getWriter()
			.append("建议前台通知和后台通知用两个类实现，后台通知进行商户的数据库等处理,前台通知实现客户浏览器跳转<br>")
			.append("签名是否正确："+ signatureCheck)
			.append("<br>交易是否成功："+"00".equals(resArr[10]));
			if(!"00".equals(resArr[10])){
				response.getWriter().append("<br>失败原因:"+resArr[11]);
			}
		} catch (IOException e) {
			
		}
	
		response.setStatus(HttpServletResponse.SC_OK);
	}
	
	
	

}
