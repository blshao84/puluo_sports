package com.puluo.api.test;

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
import com.puluo.config.Configurations
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
import com.puluo.config.ButtonKeyType
import java.util.UUID
import org.joda.time.LocalDateTime
import com.puluo.api.util._
import com.puluo.api.service.WechatTextAPI

object WechatService extends RestHelper with Loggable {

  implicit def replyMessageToResponse(msg: WechatReplyMessage): XmlResponse = msg.xmlResponse

  serve {
    //case "sns" :: "weichat" :: "create" :: Nil Get _ => createButton
    case "sns" :: "wechat" :: Nil Get _ => processReq
    case "sns" :: "wechat" :: Nil Post _ => processReq
  }

  def processReq:LiftResponse = {
    (S.param("signature"), S.param("timestamp"), S.param("nonce"), S.param("echostr")) match {
      case (Full(sig), Full(timestamp), Full(nonce), Full(echostr)) => verify(sig, timestamp, nonce, echostr)
      case _ => {
        val params = parseXml(S.request.get)
        (params.get("ToUserName"), params.get("FromUserName"), params.get("CreateTime"), params.get("MsgType")) match {
          case (Some(toUser), Some(fromUser), Some(creatAt), Some(msgType)) => {
            msgType match {
              case "text" => {
                val api = new WechatTextAPI(toUser, fromUser, creatAt, msgType, params.getOrElse("Content", ""))
                WechatTextMessage(params,api.process())
              }
              //case "image" => processImageReq(params)
              //case "event" => processButtonReq(params)
              case _ => {
                logger.error("暂时不支持微信msgType=%s的请求".format(msgType))
                PlainTextResponse("error")
              }
            }
          }
          case _ => {
            logger.error("无法获取微信发送的验证数据")
            PlainTextResponse("error")
          }
        }
      }
    }
  }

  def verify(sig: String, timestamp: String, nonce: String, echostr: String) = {
    val tmpArr = Seq(Configurations.wechatToken, timestamp, nonce).sorted
    val tmpStr = tmpArr.mkString
    val tmpStrHash = DigestUtils.shaHex(tmpStr)
    if (tmpStrHash == sig) {
      PlainTextResponse(echostr)
    } else {
      logger.error("server签名与weichat不符:signature=%s,timestamp=%s,nonce=%s,tmpArr=%s,tmpStr=%s,tmpHash=%s".
        format(sig, timestamp, nonce, tmpArr, tmpStr, tmpStrHash))
      PlainTextResponse("error")
    }
  }

  private def parseXml(req: Req): Map[String, String] = {
    val inputStream = req.rawInputStream.getOrElse(throw new Exception("微信发送的请求不包含xml数据"))
    // 读取输入流
    val reader = new SAXReader()
    val document = reader.read(inputStream)
    inputStream.close()
    // 得到xml根元素
    val root = document.getRootElement()
    val elementList = root.elements
    // 得到根元素的所有子节点
    val result = elementList.map(_ match {
      case e: Element => Some((e.getName(), e.getText()))
      case e: Any => {
        logger.error("解析微信数据错误，expect dom4j.Element, but find %s".format(e.getClass))
        None
      }
    }).flatten
    logger.info("解析微信数据结果:\n" + result.mkString("\n"))
    result.toMap
  }

}