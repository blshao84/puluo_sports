package com.puluo.api.auth

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp

object PuluoAuth extends RestHelper{
  serve {
    case "login" :: Nil Post _ => {
      PuluoSession(SessionInfo(true))
      JsonResponse("{\"status\":\"logged in\"}")
    }
    case "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(false))
      JsonResponse("{\"status\":\"logged out\"}")
    }
    case "login" :: Nil Get _ => {
      PuluoSession(SessionInfo(true))
      JsonResponse("{\"status\":\"logged in\"}")
    }
    case "logout" :: Nil Get _ => {
      PuluoSession(SessionInfo(false))
      JsonResponse("{\"status\":\"logged out\"}")
    }
  }
}