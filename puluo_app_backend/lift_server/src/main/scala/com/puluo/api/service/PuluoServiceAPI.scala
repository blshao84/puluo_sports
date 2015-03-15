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

object PuluoServiceAPI extends RestHelper with PuluoAPIUtil with SMSSender with Loggable {
  serve {
    case "services" :: "sms" :: "register" :: Nil Put _ => callWithParam(Map(
      "mobile" -> ErrorResponseResult(15).copy(message = "mobile")))(doSendRegisterSMS)

    case "dummy" :: "services" :: "sms" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(), 201)
    }
  }
  private def doSendRegisterSMS(params: Map[String, String]) = doSendSMS(params + ("sms_type" -> "UserRegistration"))

}