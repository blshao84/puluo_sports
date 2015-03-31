package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.ApiErrorResult
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import org.joda.time.DateTime
import com.puluo.api.result._
import net.liftweb.http.S
import com.puluo.session.PuluoSessionManager

object DummyAPI extends RestHelper with PuluoAPIUtil {
  serve {
    case "dummy" :: "users" :: "login" :: Nil Post _ => {
      val paramMap = PuluoResponseFactory.createParamMap(Seq("mobile", "password"))
      logger.info("\n" + paramMap.mkString("\n"))
      paramMap.get("password") match {
        case Some("invalid") => PuluoResponseFactory.createDummyJSONResponse(ApiErrorResult.getError(4).toJson(), 201)
        case _ => {
          val sessionID = S.session.map(_.httpSession.get.sessionId).getOrElse("")
          PuluoSessionManager.login(sessionID,
              new com.puluo.entity.impl.PuluoSessionImpl("", "", DateTime.now, DateTime.now))
          PuluoResponseFactory.createDummyJSONResponse(UserLoginResult.dummy().toJson(), 201)
        }
      }
    }
    case "dummy" :: "users" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserRegistrationResult.dummy().toJson(), 201)
    }
    case "dummy" :: "events" :: "payment" :: eventUUID :: Nil Post _ => { //FIXME: should be POST?
      PuluoResponseFactory.createDummyJSONResponse(EventRegistrationResult.dummy().toJson())
    }
    case "dummy" :: "services" :: "sms" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(), 201)
    }
  }

}