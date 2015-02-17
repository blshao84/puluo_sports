package com.puluo.api.event
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoEventAPI extends RestHelper {
  serve {
    case "events" :: "payment" :: eventUUID :: Nil Get _ => {
      ???
    }
    case "events" :: "detail" :: eventUUID :: Nil Get _ => {
      ???
    }
    case "events" :: "memory" :: Nil Post _ => {
      ???
    }
    case "events" :: "search" :: Nil Post _ => {
      ???
    }
  }
}