package com.puluo.api.message
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.common.Loggable
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import org.joda.time.DateTime

object PuluoMessageAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "message" :: "send" :: Nil Post _ => {
      callWithAuthParam(Map(
        "to_uuid" -> ErrorResponseResult(15).copy(message = "to_uuid"),
        "content" -> ErrorResponseResult(15).copy(message = "content"),
        "content_type" -> ErrorResponseResult(15).copy(message = "content_type")))(doSendMessage)
    }
    case "users" :: "messages" :: Nil Post _ => doListMessages

    case "users" :: "messages" :: "summary" :: Nil Post _ => doListMessageSummary
  }

  private def doSendMessage(params: Map[String, String]) = {
    val toUserUUID = params("to_uuid")
    val token = params("token")
    val content = params("content")
    val contentType = params("content_type")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    val api = new SendMessageAPI(fromUserUUID, toUserUUID, content, contentType)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, 201)
  }

  private def doListMessages = {
    val params = PuluoResponseFactory.createParamMap(Seq("token", "from_user_uuid", "to_user_uuid", "since", "limit", "offset"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = params.getOrElse("from_user_uuid", "") //session.userUUID()
    val toUserUUID = params.getOrElse("to_user_uuid", "")
    val limit = try {
      params.get("limit").get.toInt
    } catch {
      case e: Exception => 0
    }
    val offset = try {
      params.get("offset").get.toInt
    } catch {
      case e: Exception => 0
    }
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
    val api = new ListMessageAPI(fromUserUUID, toUserUUID, since, limit, offset)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
  
  private def doListMessageSummary = {
    val params = PuluoResponseFactory.createParamMap(Seq("token","limit", "offset"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID();
    val limit = try {
      params.get("limit").get.toInt
    } catch {
      case e: Exception => 0
    }
    val offset = try {
      params.get("offset").get.toInt
    } catch {
      case e: Exception => 0
    }
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
    logger.info(s"call ListMessageSummaryAPI with uuid=${userUUID},limit=${limit},offset=${offset}")
    val api = new ListMessageSummaryAPI(userUUID,limit, offset)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}