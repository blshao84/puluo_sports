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
import net.liftweb.common.Loggable
import net.liftweb.util.LiftFlowOfControlException
import scala.util.Failure
import net.liftweb.common.Box

object PuluoAuthPrivateAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "logout" :: Nil Post _ => {
      callWithParam(Map())(doLogout)
    }
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      callWithParam(Map(
        "new_password" -> ErrorResponseResult(15).copy(message="new_password"),
        "password" -> ErrorResponseResult(15).copy(message="password")))(doUpdatePassword)

    }
    case "dummy" :: "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo(None))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }

  }

  private def doLogout(params:Map[String,String]) = {
    val api = new UserLogoutAPI(PuluoSession.session)
    api.execute()
    PuluoSession(SessionInfo(None))
    println(PuluoSession.hashCode()+":"+PuluoSession.login)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doUpdatePassword(params: Map[String, String]) = {
    val newPassword = params("new_password")
    val password = params("password")
    val uuid = PuluoSession.userUUID
    val api = new UserPasswordUpdateAPI(uuid, password, newPassword)
    PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
  }
}