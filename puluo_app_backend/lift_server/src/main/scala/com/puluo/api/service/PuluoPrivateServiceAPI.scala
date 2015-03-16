package com.puluo.api.service

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
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
import net.liftweb.http.Req
import com.puluo.api.service.ImageUploadInput
import com.puluo.api.service.PuluoImageType


object PuluoPrivateServiceAPI extends RestHelper with PuluoAPIUtil with SMSSender with Loggable {
  serve {
    case "services" :: "email" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(), 201)
    }
    case "services" :: "images" :: "user" :: Nil Post req => {
      val inputs = toImageUploadInputs(req)
      val api = new ImageUploadServiceAPI(PuluoImageType.UserProfile, inputs)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "images" :: "poster" :: Nil Post req => {
      val inputs = toImageUploadInputs(req)
      val api = new ImageUploadServiceAPI(PuluoImageType.EventPoster, inputs)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "images" :: "memory" :: Nil Post req => {
      val inputs = toImageUploadInputs(req)
      val api = new ImageUploadServiceAPI(PuluoImageType.EventMemory, inputs)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "sms" :: Nil Put _ => callWithParam(Map(
      "sms_type" -> ErrorResponseResult(15).copy(message = "sms_type"),
      "mobile" -> ErrorResponseResult(15).copy(message = "mobile")))(doSendSMS)

    case "dummy" :: "services" :: "email" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "images" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ImageUploadServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "sms" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(), 201)
    }
  }

  private def toImageUploadInputs(req: Req) = req.uploadedFiles.map { fph =>
    new ImageUploadInput(fph.fileName, fph.file)
  }
}