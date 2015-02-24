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
import com.puluo.entity.impl.PuluoSessionImpl
import org.joda.time.DateTime
import net.liftweb.http.S
import net.liftweb.common.Full
import net.liftweb.common.Empty
import com.puluo.api.util.ErrorResponseResult

object PuluoAuthAPI extends RestHelper {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      (S.param("mobile"), S.param("password")) match {
        case (Full(mobile), Full(password)) => doLogin(mobile,password)
        case (Empty, _) => {
          val error = ErrorResponseResult("login", "missing mobile", "")
          PuluoResponseFactory.createErrorResponse(error)
        }
        case (_, Empty) => {
          val error = ErrorResponseResult("login", "missing password", "")
          PuluoResponseFactory.createErrorResponse(error)
        }
        case _ => {
          val error = ErrorResponseResult("login", "unknown errors", "")
          PuluoResponseFactory.createErrorResponse(error)
        }
      }
    }
    case "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(None))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "users" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
    }
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }

  }

  private def doLogin(mobile: String, password: String) = {
    val api = new UserLoginAPI(mobile, password)
    val sessionOpt = api.obtainSession
    val session = if (sessionOpt == null) None else Some(sessionOpt)
    PuluoSession(SessionInfo(session))
    PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
  }
}