package com.puluo.api

import net.liftweb.http.rest.RestHelper
import com.puluo.api.util.LoggedIn
import net.liftweb.http.OkResponse

object PuluoAuth extends RestHelper{
  serve {
    case "login" :: Nil Get _ => {
      LoggedIn(true)
      OkResponse()
    }
    case "logout" :: Nil Get _ => {
      LoggedIn(false)
      OkResponse()
    }
  }
}