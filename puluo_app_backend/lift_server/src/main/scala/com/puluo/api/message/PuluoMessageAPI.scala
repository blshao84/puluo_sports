package com.puluo.api.message
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoMessageAPI extends RestHelper {
  serve {
    case "users" :: "message" :: "send" :: Nil Put _ => {
      ???
    }
    case "users" :: "messages" :: Nil Post _ => {
      ???
    }
  }
}