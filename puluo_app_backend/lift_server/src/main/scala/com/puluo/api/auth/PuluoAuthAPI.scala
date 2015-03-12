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
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.result.ApiErrorResult
import net.liftweb.json.Printer
import net.liftweb.json.JsonAST

object PuluoAuthAPI extends RestHelper with PuluoAPIUtil {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult("login", "missing mobile", ""),
        "password" -> ErrorResponseResult("login", "missing password", "")))(doLogin)
    }
    case "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(None))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "users" :: "register" :: Nil Put _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult("register", "missing mobile", ""),
        "password" -> ErrorResponseResult("register", "missing password", "")))(doRegister)
    }
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      callWithParam(Map(
        "new_password" -> ErrorResponseResult("update password", "missing new password", ""),
        "password" -> ErrorResponseResult("update password", "missing password", "")))(doUpdatePassword)

    }
    case "dummy" :: "users" :: "login" :: Nil Post _ => {
      val json = S.request.get.json
      val jmsg = json.get \ "mobile"
      val msg = Printer.pretty(JsonAST.render(jmsg))
      msg match {
        case "\"invalid\"" => PuluoResponseFactory.createDummyJSONResponse(ApiErrorResult.getError(4).toJson(), 201)
        case _ => {
          PuluoSession(SessionInfo(Some(new UserLoginAPI("", "").obtainSession())))
          PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
        }
      }
    }
    case "dummy" :: "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(None))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
    }
    case "dummy" :: "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }

  }

  private def doLogin(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val api = new UserLoginAPI(mobile, password)
    val sessionOpt = api.obtainSession
    val session = if (sessionOpt == null) None else Some(sessionOpt)
    PuluoSession(SessionInfo(session))
    PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
  }

  private def doLogout = {
    val api = new UserLogoutAPI(PuluoSession.session)
    PuluoSession(SessionInfo(None))
    PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
  }

  private def doRegister(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val api = new UserRegistrationAPI(mobile, password, "")
    PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
  }

  private def doUpdatePassword(params: Map[String, String]) = {
    val newPassword = params("new_password")
    val password = params("password")
    val uuid = PuluoSession.userUUID
    val api = new UserPasswordUpdateAPI(uuid, password, newPassword)
    PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
  }
}