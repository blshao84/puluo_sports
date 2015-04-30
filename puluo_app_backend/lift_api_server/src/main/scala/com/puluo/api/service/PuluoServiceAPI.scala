package com.puluo.api.service
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.EmailServiceResult
import com.puluo.api.result.SMSServiceResult
import com.puluo.api.result.ImageUploadServiceResult
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.ErrorResponseResult
import java.util.HashMap
import net.liftweb.common.Loggable
import com.puluo.session.PuluoSessionManager
import com.puluo.api.event.PuluoConfigurationAPI

object PuluoServiceAPI extends RestHelper with PuluoAPIUtil with SMSSender with Loggable {
  serve {
    case "services" :: "sms" :: "register" :: Nil Post _ => callWithParam(Map(
      "mobile" -> ErrorResponseResult(15).copy(message = "mobile")))(doSendRegisterSMS)
    case "services" :: "sms" :: "reset" :: Nil Post _ => callWithParam(Map(
      "mobile" -> ErrorResponseResult(15).copy(message = "mobile")))(doSendResetSMS)
    case "services" :: "configurations" :: Nil Post _ => callWithAuthParam()(PuluoServiceAPI.doGetConfigurations)

  }
  private def doSendRegisterSMS(params: Map[String, String]) = doSendSMS(params + ("sms_type" -> "UserRegistration"))

  private def doSendResetSMS(params: Map[String, String]) = doSendSMS(params + ("sms_type" -> "PasswordReset"))

  def doGetConfigurations(params: Map[String, String]) = {
    val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    logger.info(s"user ${userUUID} is requesting event configurations")
    val api = new PuluoConfigurationAPI()
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}