package com.puluo.api.service
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoServiceAPI extends RestHelper {
  serve {
    case "services" :: "email" :: Nil Put _ => {
      ???
    }
    case "services" :: "images" :: Nil Post _ => {
      ???
    }
    case "services" :: "sms" :: Nil Put _ => {
      ???
    }
  }
}