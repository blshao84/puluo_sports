package com.puluo.api.user
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoUserAPI extends RestHelper {
  serve {
    case "users" :: mobileOrUUID :: Nil Get _ => {
      ???
    }
    case "users" :: "update" :: Nil Post _ => {
      ???
    }
    case "users" :: "search" :: Nil Post _ => {
      ???
    }
    case "users" :: "privacy" :: mobileOrUUID :: Nil Get _ => {
      ???
    }
    case "users" :: "setting" :: "update" :: Nil Post _ => {
      ???
    }
  }
}