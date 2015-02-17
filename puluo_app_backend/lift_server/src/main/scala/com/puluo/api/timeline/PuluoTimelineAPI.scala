package com.puluo.api.timeline
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoTimelineAPI extends RestHelper {
  serve {
    case "users" :: "timeline" :: Nil Post _ => {
      ???
    }
    case "users" :: "timeline" :: "like" :: Nil Post _ => {
      ???
    }
    case "users" :: "timeline" :: "delike" :: Nil Post _ => {
      ???
    }
    case "users" :: "timeline" :: "comment" :: Nil Put _ => {
      ???
    }
    case "users" :: "timeline" :: "comment" :: "delete" :: Nil Put _ => {
      ???
    }
  }
}