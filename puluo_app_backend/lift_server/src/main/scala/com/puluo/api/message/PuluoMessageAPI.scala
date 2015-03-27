package com.puluo.api.message
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.SendMessageResult
import com.puluo.api.result.ListMessageResult
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.common.Loggable
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import org.joda.time.DateTime

object PuluoMessageAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "message" :: "send" :: Nil Put _ => {
      callWithParam(Map(
        "token" -> ErrorResponseResult(15).copy(message = "token"),
        "to_uuid" -> ErrorResponseResult(15).copy(message = "to_uuid"),
        "content" -> ErrorResponseResult(15).copy(message = "content"),
        "content_type" -> ErrorResponseResult(15).copy(message = "content_type")))(doSendMessage)
    }
    case "users" :: "messages" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListMessageResult.dummy().toJson())
    }
  }

  private def doSendMessage(params: Map[String, String]) = {
    val toUserUUID = params("to_uuid")
    val token = params("token")
    val content = params("content")
    val contentType = params("content_type")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    val api = new SendMessageAPI( fromUserUUID, toUserUUID, content, contentType)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, 201)
  }

  private def doListMessages = {
    val params = PuluoResponseFactory.createParamMap(Seq("token", "user_uuid", "since"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    val toUserUUID = params.getOrElse("user_uuid", "")
    val since = params.get("since").map {
      s =>
        try {
          Some(new DateTime(s.toLong))
        } catch {
          case e: Exception => {
            logger.warn(s"'since' param has wrong format, expecting unix timestamp, but was $s")
            None
          }
        }
    }.flatten.getOrElse(null)
    val api = new ListMessageAPI(fromUserUUID,toUserUUID,since)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}