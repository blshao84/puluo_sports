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
    case "users" :: mobileOrUUID :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse(UserProfileResult.dummy().toJson())
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
  }
}