package com.puluo.api.payment

import java.util.HashMap

import scala.xml.XML

import com.puluo.api.util.PuluoAPIUtil

import net.liftweb.common.Box
import net.liftweb.common.Box.box2Option
import net.liftweb.common.Full
import net.liftweb.common.Loggable
import net.liftweb.http.ForbiddenResponse
import net.liftweb.http.LiftResponse
import net.liftweb.http.PlainTextResponse
import net.liftweb.http.Req
import net.liftweb.http.S
import net.liftweb.http.rest.RestHelper

object AliPaymentNotification extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "payment" :: "alipay" :: "notify" :: Nil Get _ => doNotify
    case "payment" :: "alipay" :: "notify" :: Nil Post _ => doNotify
  }

  def doNotify: Box[LiftResponse] = {
    logger.info("received alipay notify_url request")
    try {
      val api = createPaymentAPI(S.request.get)
      safeRun(api)
      val result = api.getPaymentResult();
      if (result.isSuccess()) {
        Full(PlainTextResponse("success"))
      } else {
        Full(ForbiddenResponse(result.error()))
      }
    } catch {
      case e: Exception => Full(ForbiddenResponse("error request"))
    }
  }

  def createPaymentAPI(request: Req) = {

    val params = new HashMap[String, String]();
    request.params.foreach(p => params.put(p._1, p._2.mkString(",")))
    val notifyData = new String(request.param("notify_data").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    val notifyXML = XML.loadString(notifyData)

    //商户订单号
    val tradeID = notifyXML \\ "out_trade_no" //new String(request.param("out_trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //支付宝交易号
    val paymentRef = notifyXML \\ "trade_no" //new String(request.param("trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //交易状态
    val trade_status = notifyXML \\ "trade_status" //new String(request.param("trade_status").getOrElse("").getBytes("ISO-8859-1"), "UTF-8")
    new PuluoAlipayAPI(params, trade_status.text, tradeID.text, paymentRef.text,false)
  }

}