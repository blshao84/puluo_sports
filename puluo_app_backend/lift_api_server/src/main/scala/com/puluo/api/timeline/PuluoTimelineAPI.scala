package com.puluo.api.timeline
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.result._
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.common.Loggable
import com.puluo.session.PuluoSessionManager
import net.liftweb.http.LiftResponse
import com.puluo.util.TimeUtils
import org.joda.time.DateTime
import com.puluo.util.Strs
import com.puluo.api.util.ErrorResponseResult

object PuluoTimelineAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "timeline" :: "get" :: userUUID :: Nil Post _ => doGetUserTimeline(userUUID)
    case "users" :: "timeline" :: "like" :: Nil Post _ => callWithAuthParam(Map(
        "timline_uuid" -> ErrorResponseResult(15).copy(message = "timline_uuid"))
        )(doLikeTimeline)
    case "users" :: "timeline" :: "like" :: "delete" :: Nil Post _ => callWithAuthParam(Map(
        "timline_uuid" -> ErrorResponseResult(15).copy(message = "timline_uuid"))
        )(doDeleteTimelineLike)
    case "users" :: "timeline" :: "comment" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(CommentTimelineResult.dummy().toJson())
    }
    case "users" :: "timeline" :: "comment" :: "delete" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteTimelineCommentResult.dummy().toJson())
    }

  }
  private def doLikeTimeline(params:Map[String,String]) = {
    val timelineUUID = params("timeline_uuid")
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    val api = new LikeTimelineAPI(timelineUUID,userUUID)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
  
  private def doDeleteTimelineLike(params:Map[String,String]) = {
    val timelineUUID = params("timeline_uuid")
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val userUUID = session.userUUID()
    val api = new RemoveTimelineLikeAPI(timelineUUID,userUUID)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
  
  
  private def doGetUserTimeline(userUUID: String): LiftResponse = {
    val params = PuluoResponseFactory.createParamMap(Seq("since_time", "offset", "limit"))
    val sinceTime: String = params.get("since_time").map(t =>
      try {
        val unixTimestamp = t.toLong
        TimeUtils.formatDate(new DateTime(unixTimestamp))
      } catch {
        case e: Exception => null
      }).getOrElse(null)
    val limit = params.get("limit") match {
      case Some(s) if Strs.isLong(s) => s.toInt
      case _ => 100
    }
    val offset = params.get("offset") match {
      case Some(s) if Strs.isLong(s) => s.toInt
      case _ => 0
    }
    val api = new UserTimelineAPI(userUUID,sinceTime,limit,offset)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}