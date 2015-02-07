package com.puluo.api

import net.liftweb.http.rest.RestHelper
import com.puluo.api.util.LoggedIn
import net.liftweb.http.OkResponse
import net.liftweb.http.JsonResponse

object PuluoAuth extends RestHelper{
  serve {
    case "login" :: Nil Post _ => {
      LoggedIn(true)
      JsonResponse("{\"status\":\"logged in\"}")
    }
    case "logout" :: Nil Post _ => {
      LoggedIn(false)
      JsonResponse("{\"status\":\"logged out\"}")
    }
    case "login" :: Nil Get _ => {
      LoggedIn(true)
      JsonResponse("{\"status\":\"logged in\"}")
    }
    case "logout" :: Nil Get _ => {
      LoggedIn(false)
      JsonResponse("{\"status\":\"logged out\"}")
    }
  }
}