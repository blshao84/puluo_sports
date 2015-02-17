package com.puluo.api.auth

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoAuthAPI extends RestHelper {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      PuluoSession(SessionInfo(true))
      JsonResponse("{\"status\":\"logged in\"}")
    }
    case "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(false))
      JsonResponse("{\"status\":\"logged out\"}")
    }
    case "users" :: "register" :: Nil Put _ => {
      ???
    }
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      ???
    }

  }
}