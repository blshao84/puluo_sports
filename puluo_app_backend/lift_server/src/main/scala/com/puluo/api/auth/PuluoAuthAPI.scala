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
import com.puluo.dao.impl.DaoApi

object PuluoAuthAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "login" :: Nil Post _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult(15).copy(message = "mobile"),
        "password" -> ErrorResponseResult(15).copy(message = "password")))(doLogin)
    }
    case "users" :: "register" :: Nil Put _ => {
      callWithParam(Map(
        "mobile" -> ErrorResponseResult(15).copy(message = "mobile"),
        "password" -> ErrorResponseResult(15).copy(message = "password"),
        "code" -> ErrorResponseResult(15).copy(message = "auth_code")))(doRegister)
    }
    case "dummy" :: "users" :: "login" :: Nil Post _ => {
      val paramMap = PuluoResponseFactory.createParamMap(Seq("mobile", "password"))
      logger.info("\n" + paramMap.mkString("\n"))
      paramMap.get("password") match {
        case Some("invalid") => PuluoResponseFactory.createDummyJSONResponse(ApiErrorResult.getError(4).toJson(), 201)
        case _ => {
          PuluoSession(SessionInfo("",
            Some(new com.puluo.entity.impl.PuluoSessionImpl("", "", DateTime.now, DateTime.now))))
          PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
        }
      }
    }
    case "dummy" :: "users" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
    }
  }

  private def doLogin(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val sessionID = S.session.map(_.httpSession.get.sessionId).getOrElse("")
    logger.info(String.format("生成UserLoginAPI(%s,password,%s)", mobile, sessionID))
    val api = new UserLoginAPI(mobile, password, sessionID)
    api.execute();
    val sessionOpt = api.obtainSession
    if (sessionOpt == null){
      PuluoSession(SessionInfo("",None))
    }else {
      val uuid = DaoApi.getInstance().userDao().getByMobile(mobile).userUUID();
      PuluoSession(SessionInfo(uuid,Some(sessionOpt)))
    }
    
    PuluoResponseFactory.createJSONResponse(api, 201)
  }
  private def doRegister(params: Map[String, String]) = {
    val mobile = params("mobile")
    val password = params("password")
    val authCode = params("auth_code")
    val api = new UserRegistrationAPI(mobile, password, authCode)
    api.execute()
    PuluoResponseFactory.createJSONResponse(api, 201)
  }
}