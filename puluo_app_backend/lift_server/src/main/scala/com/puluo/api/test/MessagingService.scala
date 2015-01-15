/*package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import com.puluo.util.SMSClient
import net.liftweb.http.S
import net.liftweb.http.JsonResponse
import net.liftweb.json.JsonAST.JString
import net.liftweb.json.JsonDSL._
import net.liftweb.http.LiftResponse
import net.liftweb.common.Full
import com.puluo.util.JuheSMSClient
import net.liftweb.http.PlainTextResponse
import com.puluo.test.TestConstants
object MessagingService extends RestHelper {
  serve {
    case "login" :: _ Get _ => {
      val x = new TestConstants().login
      JsonResponse(x)
      //PlainTextResponse(x)

    }
    case "admin" :: "sms" :: "stat" :: _ Get _ => smsUsageStat
    case "admin" :: "sms" :: "activate" :: _ Get _ => smsActivate
    case "admin" :: "sms" :: "logout" :: _ Get _ => smsLogout
    case "admin" :: "sms" :: "send" :: _ Get _ => smsSend
  }

  def smsSend = {
    entitle(() => {
      val msgTmp = S.param("msg")
      val mobileTmp = S.param("mobile")
      (msgTmp, mobileTmp) match {
        case (Full(msg), Full(mobile)) => {
          val status = SMSClient.sendSMS(Seq(mobile), msg)
          if (status == 0)
            JsonResponse(Map("状态" -> ("成功发送短信至" + mobile)))
          else
            JsonResponse(Map("状态" -> "发送短信失败"))
        }
        case _ => JsonResponse(Map("状态" -> "没有指定发送内容或者号码"))
      }
    })
  }

  def smsActivate = {
    entitle(() => {
      val status = SMSClient.registEx
      if (status == 0)
        JsonResponse(Map("状态" -> "激活成功"))
      else
        JsonResponse(Map("状态" -> "激活失败"))
    })
  }
  def smsLogout = {
    entitle(() => {
      val status = SMSClient.logout
      if (status == 0)
        JsonResponse(Map("状态" -> "停止SMS服务成功"))
      else
        JsonResponse(Map("状态" -> "停止SMS服务失败"))
    })
  }

  def smsUsageStat = {
    entitle(() => {
      val balance = SMSClient.getBalance
      JsonResponse(Map("余额" -> balance.toString))
    })
  }

  def entitle(func: () => LiftResponse) = {
    if (S.loggedIn_?) {
      func()
    } else {
      JsonResponse(Map("error" -> "用户没有登录"))
    }
  }

}*/