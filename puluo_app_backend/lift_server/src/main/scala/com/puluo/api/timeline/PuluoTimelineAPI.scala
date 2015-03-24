package com.puluo.api.timeline
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.UserTimelineResult
import com.puluo.api.result.LikeTimelineResult
import com.puluo.api.result.CommentTimelineResult
import com.puluo.api.result.DeleteTimelineCommentResult

object PuluoTimelineAPI extends RestHelper {
  serve {
    case "users" :: "timeline" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserTimelineResult.dummy().toJson())
    }
    case "users" :: "timeline" :: "like" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(LikeTimelineResult.dummy().toJson())
    }
    case "users" :: "timeline" :: "delike" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(LikeTimelineResult.dummy().toJson())
    }
    case "users" :: "timeline" :: "comment" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(CommentTimelineResult.dummy().toJson())
    }
    case "users" :: "timeline" :: "comment" :: "delete" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteTimelineCommentResult.dummy().toJson())
    }
    
       
  }
}