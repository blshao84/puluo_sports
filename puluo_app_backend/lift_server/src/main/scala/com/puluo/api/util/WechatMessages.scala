package com.puluo.api.util
import net.liftweb.http.rest.RestHelper
import net.liftweb.util.Helpers._
import net.liftweb.http._
import net.liftweb.common.Full
import net.liftweb.common.Loggable
import net.liftweb.json.Serialization
import net.liftweb.json.DefaultFormats
import net.liftweb.json._
import java.util.Date
import org.joda.time.LocalDate
import org.apache.commons.codec.digest.DigestUtils
import net.liftweb.util.PCDataXmlParser
import scala.xml.NodeSeq
import org.dom4j.io.SAXReader
import scala.collection.JavaConversions._
import org.dom4j.Element
import net.liftweb.mapper._
import scala.util.Random
import net.liftweb.mapper.MaxRows
import net.liftweb.mapper.OrderBy
import net.liftweb.mapper.Descending
import net.liftweb.util.FieldError
import java.util.UUID
import org.joda.time.LocalDateTime
import com.puluo.api.result.wechat.WechatArticleMessage

trait WechatReplyMessage {
  def params: Map[String, String]
  def toUserName = params("FromUserName")
  def fromUserName = params("ToUserName")
  def createTime = params("CreateTime")
  def xmlResponse: XmlResponse
}

object WechatTextMessage {
  def apply(params: Map[String, String],
    result: com.puluo.api.result.wechat.WechatTextMessage): WechatTextMessage = {
    WechatTextMessage(params, result.content);
  }
}
case class WechatTextMessage(params: Map[String, String], val textContent: String) extends WechatReplyMessage with Loggable {
  val msgType = "text"
  def xmlResponse = {
    val xml = <xml>
                <ToUserName>{ toUserName }</ToUserName>
                <FromUserName>{ fromUserName }</FromUserName>
                <CreateTime>{ createTime }</CreateTime>
                <MsgType>{ msgType }</MsgType>
                <Content>{ textContent }</Content>
              </xml>
    logger.info("回复给微信的response：\n%s".format(xml))
    XmlResponse(xml)
  }
}

object WechatNewsMessage {
  def apply(params: Map[String, String],
    result: com.puluo.api.result.wechat.WechatNewsMessage): WechatNewsMessage = {
    WechatNewsMessage(params, result.articles.map(WechatArticleMessage(_)))
  }
}
case class WechatNewsMessage(params: Map[String, String],
  val articles: Seq[WechatArticleMessage]) extends WechatReplyMessage with Loggable {
  val msgType = "news"
  def articleCount = articles.size
  def xmlResponse = {
    val xml = <xml>
                <ToUserName>{ toUserName }</ToUserName>
                <FromUserName>{ fromUserName }</FromUserName>
                <CreateTime>{ createTime }</CreateTime>
                <MsgType>{ msgType }</MsgType>
                <ArticleCount>{ articleCount }</ArticleCount>
                <Articles>{ articles.map(_.xmlResponse) }</Articles>
              </xml>
    logger.info("回复给微信的response：\n%s".format(xml))
    XmlResponse(xml)
  }
}

object WechatArticleMessage {
  def apply(result: com.puluo.api.result.wechat.WechatArticleMessage): WechatArticleMessage = {
    WechatArticleMessage(result.title, result.description, result.image_url, result.page_url);
  }
}
case class WechatArticleMessage(val title: String, val description: String, imgUrl: String, pageUrl: String) {
  def xmlResponse =
    <item>
      <Title>{ title }</Title>
      <Description>{ description }</Description>
      <PicUrl>{ imgUrl }</PicUrl>
      <Url>{ pageUrl }</Url>
    </item>
}
	
