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
import com.puluo.session.PuluoSessionManager

object PuluoAuthPrivateAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "logout" :: Nil Post _ => callWithAuthParam()(doLogout)
    case "users" :: "credential" :: "update" :: Nil Post _ => {
      callWithAuthParam(Map(
        "new_password" -> ErrorResponseResult(15).copy(message = "new_password"),
        "password" -> ErrorResponseResult(15).copy(message = "password")))(doUpdatePassword)

    }

  }

  private def doLogout(params: Map[String, String]) = {
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val api = new UserLogoutAPI(session)
    safeRun(api)
    PuluoSessionManager.logout(token)
    logger.info(String.format("successfully logout token=%s, user_uuid=%s", token, session.userUUID))
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doUpdatePassword(params: Map[String, String]) = {
    val newPassword = params("new_password")
    val password = params("password")
    val uuid = PuluoSession.userUUID
    val api = new UserPasswordUpdateAPI(uuid, password, newPassword)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}