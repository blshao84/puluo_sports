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

object DummyPrivateAPI extends RestHelper with PuluoAPIUtil {
  serve {
    case "dummy" :: "users" :: "logout" :: Nil Post _ => {
      val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
      PuluoSessionManager.logout(token)
      PuluoResponseFactory.createDummyJSONResponse(UserLogoutResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "credential" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserPasswordUpdateResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "detail" :: eventUUID :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventDetailResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "memory" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventMemoryResult.dummy().toJson())
    }
    case "dummy" :: "events" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EventSearchResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: mobileOrUUID :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListFriendsResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "friends" :: "request" :: Nil Post _ => {
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
    case "dummy" :: "users" :: "message" :: "send" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(SendMessageResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "messages" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ListMessageResult.dummy().toJson())
    }
    case "dummy" :: "services" :: "email" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(EmailServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "images" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(ImageUploadServiceResult.dummy().toJson(), 201)
    }
    case "dummy" :: "services" :: "sms" :: Nil Post _ => {
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
    case "dummy" :: "users" :: "timeline" :: "comment" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(CommentTimelineResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "timeline" :: "comment" :: "delete" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(DeleteTimelineCommentResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "status" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse("{\"login\":true}")
    }

    case "dummy" :: "users" :: "profile" :: mobileOrUUID :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileUpdateResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSearchResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "privacy" :: mobileOrUUID :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingResult.dummy().toJson())
    }
    case "dummy" :: "users" :: "setting" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingUpdateResult.dummy().toJson())
    }
  }

}