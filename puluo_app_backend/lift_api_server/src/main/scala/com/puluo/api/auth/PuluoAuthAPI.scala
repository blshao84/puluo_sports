package com.puluo.api.auth

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.entity.impl.PuluoSessionImpl
import org.joda.time.DateTime
import net.liftweb.http.S
import net.liftweb.common.Full
import net.liftweb.common.Empty
import com.puluo.api.util.ErrorResponseResult
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.json.Printer
import net.liftweb.json.JsonAST
import net.liftweb.common.Loggable
import net.liftweb.util.LiftFlowOfControlException
import scala.util.Failure
import net.liftweb.common.Box
import com.puluo.dao.impl.DaoApi
import com.puluo.session.PuluoSessionManager

object PuluoAuthAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult(15).copy(message = "mobile"),
        "password" -> ErrorResponseResult(15).copy(message = "password")))(doLogin)
    }
    case "users" :: "register" :: Nil Post _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult(15).copy(message = "mobile"),
        "password" -> ErrorResponseResult(15).copy(message = "password"),
        "auth_code" -> ErrorResponseResult(15).copy(message = "auth_code")))(doRegister)
    }
    case "users" :: "credential" :: "reset" :: Nil Post _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult(15).copy(message = "mobile"),
        "new_password" -> ErrorResponseResult(15).copy(message = "new_password"),
        "auth_code" -> ErrorResponseResult(15).copy(message = "auth_code")))(doUpdatePassword)

    }
  }

  private def doUpdatePassword(params: Map[String, String]) = {
    val newPassword = params("new_password")
    val authCode = params("auth_code")
    val mobile = params("mobile")
    val user = DaoApi.getInstance().userDao().getByMobile(mobile)
    if (user != null) {
      val uuid = user.userUUID()
      val api = new UserPasswordUpdateAPI(uuid, authCode, newPassword)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    } else PuluoResponseFactory.createErrorResponse(ErrorResponseResult(18))
  }

  private def doLogin(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val sessionID = S.session.map(_.httpSession.get.sessionId).getOrElse("")
    logger.info(String.format("生成UserLoginAPI(%s,password,%s)", mobile, sessionID))
    val api = new UserLoginAPI(mobile, password, sessionID)
    safeRun(api);
    val sessionOpt = api.obtainSession
    if (sessionOpt != null) {
      val uuid = DaoApi.getInstance().userDao().getByMobile(mobile).userUUID();
      PuluoSessionManager.login(sessionID, sessionOpt)
      //PuluoSession(SessionInfo(uuid,Some(sessionOpt)))
    }

    PuluoResponseFactory.createJSONResponse(api, 201)
  }
  private def doRegister(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val authCode = params("auth_code")
    val api = new UserRegistrationAPI(mobile, password, authCode)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, 201)
  }
}