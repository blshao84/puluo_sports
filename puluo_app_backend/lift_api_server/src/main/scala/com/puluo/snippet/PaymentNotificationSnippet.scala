package com.puluo.snippet

import scala.collection.JavaConversions._
import net.liftweb.http.RequestVar
import net.liftweb.util.Helpers._
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds
import net.liftweb.common.Empty
import net.liftweb.http.js.JsCmd
import net.liftweb.http.SHtml.ChoiceHolder
import net.liftweb.http.js.JsCmds.ReplaceOptions
import net.liftweb.common.Full
import org.joda.time.LocalDate
import net.liftweb.http.S
import net.liftweb.common.Loggable
import net.liftweb.http.LiftRules
import net.liftweb.http.js.JsCmds._
import net.liftweb.sitemap.Loc.Value
import bootstrap.liftweb.Boot
import net.liftweb.util.PassThru
import com.puluo.dao.impl.DaoApi
import com.puluo.util.TimeUtils
import com.puluo.util.Strs
import com.puluo.snippet.util.PuluoSnippetUtil
import com.puluo.api.event.EventRegistrationAPI
import com.puluo.config.Configurations
import com.puluo.api.payment.AliPaymentNotification
import com.puluo.api.payment.PuluoAlipayAPI
import net.liftweb.http.Req
import scala.xml.XML
import java.util.HashMap

object PaymentNotificationSnippet extends PuluoSnippetUtil with Loggable {
  val errorMsg = "抱歉，支付出现异常，如果您没有收到短信确认的通知，请于我们客服联系，电话：010-59003866"
  def render = try {
    val req = S.request
    if (req.isDefined) {
      logger.info("req is defined")
      val api = createPaymentAPI(req.get)
      if (api.isPaymentSuccess()) {
        api.processValidRequest()
      }
      if (api.isSuccess) {
        val dsi = DaoApi.getInstance()
        val order = dsi.paymentDao().getOrderByNumericID(api.orderNumericID)
        if (order != null) {
          val event = dsi.eventDao().getEventByUUID(order.eventId())
          "#order_id *" #> order.orderNumericID() &
            "#event_name *" #> event.eventInfo().name() &
            "#event_time *" #> TimeUtils.formatDate(event.eventTime()) &
            "#event_location *" #> event.eventLocation().address() &
            "#order_status *" #> order.status().toString()
        } else {
          "#notification" #> errorMsg
        }
      } else {
        "#notification" #> errorMsg
      }
    } else {
      "#notification" #> errorMsg
    }
  } catch {
    case e: Exception => {
      logger.error("encounter error in executing API ...")
      logger.error(e.getStackTraceString)
      "#notification" #> errorMsg
    }
  }

  def createPaymentAPI(request: Req) = {

    val params = new HashMap[String, String]();
    request.params.foreach(p => params.put(p._1, p._2.mkString(",")))
    logger.info("params returned by notification url:" + params)
    //商户订单号
    val tradeID = new String(request.param("out_trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //支付宝交易号
    val paymentRef = new String(request.param("trade_no").getOrElse("").getBytes("ISO-8859-1"), "UTF-8");
    //交易状态
    val trade_status = new String(request.param("result").getOrElse("").getBytes("ISO-8859-1"), "UTF-8")
    logger.info(s"tradeID=${tradeID},paymentRef=${paymentRef},trade_status=${trade_status}")
    new PuluoAlipayAPI(params, trade_status, tradeID, paymentRef, true)
  }
}