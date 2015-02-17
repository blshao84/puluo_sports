package com.puluo.api.auth

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.UserLoginResult
import com.puluo.api.result.UserLogoutResult
import com.puluo.api.result.UserRegistrationResult
import com.puluo.api.result.UserPasswordUpdateResult

object PuluoAuthAPI extends RestHelper {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      PuluoSession(SessionInfo(true))
      PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
    }
    case "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(false))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "users" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
    }
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }

  }
}