package com.puluo.api.user
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.result.UserProfileResult
import com.puluo.api.result.UserProfileUpdateResult
import com.puluo.api.result.UserSearchResult
import com.puluo.api.result.UserSettingResult
import com.puluo.api.result.UserSettingUpdateResult

object PuluoUserAPI extends RestHelper {
  serve {
    case "users" :: "status" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse("{\"login\":true}")
    }
    case "users" :: mobileOrUUID :: Nil Get _ => {
      val api = new UserProfileAPI(mobileOrUUID)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileUpdateResult.dummy().toJson())
    }
    case "users" :: "search" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSearchResult.dummy().toJson())
    }
    case "users" :: "privacy" :: mobileOrUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingResult.dummy().toJson())
    }
    case "users" :: "setting" :: "update" :: Nil Post _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserSettingUpdateResult.dummy().toJson())
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