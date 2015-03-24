package com.unuotech.snippet.api

import net.liftweb.http.rest.RestHelper
import scala.collection.JavaConversions._
import net.liftweb.common.Loggable
import net.liftweb.http.LiftResponse
import net.liftweb.http.Req
import net.liftweb.http.ForbiddenResponse
import net.liftweb.common.Box
import net.liftweb.common.Full
import net.liftweb.http.PlainTextResponse
import net.liftweb.common.Box._
import net.liftweb.http.S
import com.puluo.api.payment.PuluoAlipayAPI
import java.util.HashMap
import com.puluo.api.result.AlipaymentResult
import com.puluo.api.util.PuluoAPIUtil

object AliPaymentNotification extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "payment" :: "alipay" :: "notify" :: Nil Get _ => doNotify
    case "payment" :: "alipay" :: "notify" :: Nil Post _ => doNotify
  }

  def doNotify: Box[LiftResponse] = {
    logger.info("接收到支付宝notify_url的request")
    val api = createPaymentAPI(S.request.get)
    safeRun(api)
    val result = api.getPaymentResult();
    if (result.isSuccess()) {
      Full(PlainTextResponse("success"))
    } else {
      Full(ForbiddenResponse(result.error()))
    }
  }

  def createPaymentAPI(request: Req) = {

    val params = new HashMap[String, String]();
    request.params.foreach(p => params.put(p._1, p._2.mkString(",")))
    //商户订单号
    val tradeID = new String(request.param("out_trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //支付宝交易号
    val paymentRef = new String(request.param("trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //交易状态
    val trade_status = new String(request.param("trade_status").getOrElse("").getBytes("ISO-8859-1"), "UTF-8")
    new PuluoAlipayAPI(params, trade_status, tradeID,paymentRef)
  }

}