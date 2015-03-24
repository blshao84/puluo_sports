package com.puluo.api.test

import net.liftweb.http.rest.RestHelper
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.ApiErrorResult
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import org.joda.time.DateTime
import com.puluo.api.result._

object DummyAPI extends RestHelper with PuluoAPIUtil {
  serve {
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
    case "dummy" :: "users" :: "logout" :: Nil Post _ => {
      PuluoSession(SessionInfo("", None))
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "payment" :: eventUUID :: Nil Get _ => { //FIXME: should be POST?
      PuluoResponseFactory.createDummyJSONResponse(EventRegistrationResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "detail" :: eventUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventDetailResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "memory" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventMemoryResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventSearchResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListFriendsResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: "request" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(RequestFriendResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: "delete" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteFriendResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: "deny" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DenyFriendResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: "approve" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ApproveFriendResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "message" :: "send" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SendMessageResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "messages" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListMessageResult.dummy().toJson())
    }
    case "dummy" :: "services" :: "email" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "images" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ImageUploadServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "sms" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "sms" :: "register" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(SMSServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "users" :: "timeline" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserTimelineResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "timeline" :: "like" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(LikeTimelineResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "timeline" :: "delike" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(LikeTimelineResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "timeline" :: "comment" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(CommentTimelineResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "timeline" :: "comment" :: "delete" :: Nil Put _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteTimelineCommentResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "status" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse("{\"login\":true}")
    }

    case "dummy" :: "users" :: mobileOrUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileUpdateResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSearchResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "privacy" :: mobileOrUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "setting" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingUpdateResult.dummy().toJson())
    }
  }

}