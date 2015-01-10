package com.unuotech.util

import net.liftweb.common.Loggable
import org.apache.http.impl.client.DefaultHttpClient
import java.net.URLEncoder
import org.apache.http.client.methods.HttpGet
import java.net.URI
import java.io.ByteArrayOutputStream
import java.io.InputStream
import net.liftweb.json.JValue
import net.liftweb.json._
import com.unuotech.config.Configurations
import bootstrap.liftweb.Boot
import com.unuotech.model.sales.Stock
import com.unuotech.model.user.WeiChatCheckIn
import org.joda.time.LocalDate
import net.liftweb.mapper.By

object JuheSMSResult {
  def empty = JuheSMSResult("", JuheSMSResultDetail(-1, -1, -1), -1)
}
case class JuheSMSResult(reason: String, result: JuheSMSResultDetail, error_code: Int) {
  implicit val formats = DefaultFormats
  lazy val json = Serialization.write(this)
}
case class JuheSMSResultDetail(count: Int, fee: Int, sid: Long)

object JuheSMSClient extends Loggable {
  implicit val formats = DefaultFormats
  lazy val client = new DefaultHttpClient()
  val urlBase = """http://v.juhe.cn/sms/send?key=172b8ec174b8a780cd7ac841386ac502&dtype=json&"""

  
  private def doSend(templateId: Int, templateValue: String, mobile: String): JuheSMSResult = {
    val url = new StringBuilder(urlBase).append("mobile=").append(mobile).
      append("&tpl_id=").append(templateId).append("&tpl_value=").append(URLEncoder.encode(templateValue, "utf-8")).toString
    logger.info("向短信服务发送内容%s,请求%s".format(templateValue, url))
    val httpGet = new HttpGet(url)
    val response = client.execute(httpGet)
    val data = response.getEntity().getContent()
    val body = inputstream2String(data)
    logger.info("短信发送结果:%s".format(body))
    httpGet.releaseConnection()
    parse(body).extractOpt[JuheSMSResult].getOrElse(JuheSMSResult.empty)
  }
  private def inputstream2String(is: InputStream) = {
    val baos = new ByteArrayOutputStream()
    var i: Int = -1
    i = is.read()
    while (i != -1) {
      baos.write(i)
      i = is.read()
    }
    baos.toString()
  }

}