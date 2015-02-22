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

object PuluoServiceAPI extends RestHelper {
  serve {
    case "services" :: "email" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(),201)
    }
    case "services" :: "images" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ImageUploadServiceResult.dummy().toJson(),201)
    }
    case "services" :: "sms" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(),201)
    }
  }
}