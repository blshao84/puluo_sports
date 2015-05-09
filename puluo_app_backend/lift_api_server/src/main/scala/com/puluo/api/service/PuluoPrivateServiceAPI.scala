package com.puluo.api.service

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.ErrorResponseResult
import java.util.HashMap
import net.liftweb.common.Loggable
import net.liftweb.http.Req
import com.puluo.enumeration.PuluoImageType
import com.puluo.session.PuluoSessionManager
import net.liftweb.http.LiftResponse
import com.puluo.result.EmailServiceResult

object PuluoPrivateServiceAPI extends RestHelper with PuluoAPIUtil with SMSSender with Loggable {
  serve {
    case "services" :: "email" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(), 201)
    }
    case "services" :: "images" :: "upload" :: Nil Post req => {
      val param = Map(
        "usage" -> ErrorResponseResult(15).copy(message = "usage"))
      val func = doUploadImage(req)
      callWithAuthParam(param)(func)
    }

    case "services" :: "images" :: "user" :: Nil Post req => {
      val params = PuluoResponseFactory.createParamMap(Seq("token", "event_uuid", "mock"))
      val mock = params.get("mock").map(_ == "true").getOrElse(false);
      val token = params("token")
      val userUUID = PuluoSessionManager.getUserUUID(token)
      val inputs = toImageUploadInputs(req)
      val api = new ImageUploadServiceAPI(PuluoImageType.UserProfile, userUUID, null, inputs, mock)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "images" :: "poster" :: Nil Post req => {
      val inputs = toImageUploadInputs(req)
      val params = PuluoResponseFactory.createParamMap(Seq("token", "event_uuid", "mock"))
      val mock = params.get("mock").map(_ == "true").getOrElse(false);
      val token = params("token")
      val eventUUID = params.get("event_uuid").getOrElse("")
      val userUUID = PuluoSessionManager.getUserUUID(token)
      val api = new ImageUploadServiceAPI(PuluoImageType.EventPoster, userUUID, eventUUID, inputs, mock)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "images" :: "memory" :: Nil Post req => {
      val inputs = toImageUploadInputs(req)
      val params = PuluoResponseFactory.createParamMap(Seq("token", "event_uuid", "mock"))
      val mock = params.get("mock").map(_ == "true").getOrElse(false);
      val token = params("token")
      val eventUUID = params.get("event_uuid").getOrElse("")
      val userUUID = PuluoSessionManager.getUserUUID(token)
      val api = new ImageUploadServiceAPI(PuluoImageType.EventMemory, userUUID, eventUUID, inputs, mock)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api, 201)
    }
    case "services" :: "sms" :: Nil Post _ => callWithParam(Map(
      "sms_type" -> ErrorResponseResult(15).copy(message = "sms_type"),
      "mobile" -> ErrorResponseResult(15).copy(message = "mobile")))(doSendSMS)

  }

  private def doUploadImage(req: Req): (Map[String, String] => LiftResponse) = {
    case param:Map[String,String] =>
      val inputs = toImageUploadInputs(req)
      val params = PuluoResponseFactory.createParamMap(Seq("token", "event_uuid", "mock"))
      val usage = param("usage") match {
        case "user" => PuluoImageType.UserProfile
        case "poster" => PuluoImageType.EventPoster
        case "memory" => PuluoImageType.EventMemory
      }
      val mock = params.get("mock").map(_ == "true").getOrElse(false);
      val token = params("token")
      val eventUUID = params.get("event_uuid").getOrElse("")
      val userUUID = PuluoSessionManager.getUserUUID(token)
      val api = new ImageUploadServiceAPI(usage, userUUID, eventUUID, inputs, mock)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api, 201)
  }
  private def toImageUploadInputs(req: Req) = req.uploadedFiles.map { fph =>
    new ImageUploadInput(fph.fileName, fph.file)
  }
}