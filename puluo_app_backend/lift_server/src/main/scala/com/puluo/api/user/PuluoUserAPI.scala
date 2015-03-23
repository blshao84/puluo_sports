package com.puluo.api.user
import scala.collection.JavaConversions._
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
import net.liftweb.common.Loggable
import com.puluo.api.setting.UserSettingAPI
import com.puluo.api.util.PuluoAPIUtil
import com.puluo.api.setting.UserSettingUpdateAPI

object PuluoUserAPI extends RestHelper with PuluoAPIUtil with Loggable {
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
      val optionalParams = Seq(
        "first_name", "last_name", "thumbnail", "large_image", "saying",
        "email", "sex", "birthday", "country", "state", "city", "zip")
      val paramMap = PuluoResponseFactory.createParamMap(optionalParams)
      logger.info("param map for users/update:\n" + paramMap.mkString("\n"))
      val uuid = PuluoSession.userUUID
      val api = new UserProfileUpdateAPI(
        uuid, paramMap.getOrElse("first_name", ""), paramMap.getOrElse("last_name", ""),
        paramMap.getOrElse("thumbnail", ""), paramMap.getOrElse("large_image", ""),
        paramMap.getOrElse("saying", ""), paramMap.getOrElse("email", ""),
        paramMap.getOrElse("sex", ""), paramMap.getOrElse("birthday", ""),
        paramMap.getOrElse("country", ""), paramMap.getOrElse("state", ""),
        paramMap.getOrElse("city", ""), paramMap.getOrElse("zip", ""));
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "search" :: Nil Post _ => {
      val optionalParams = Seq("first_name", "last_name", "email", "mobile")
      val paramMap = PuluoResponseFactory.createParamMap(optionalParams)
      logger.info("param map for users/search:\n" + paramMap.mkString("\n"))
      val api = new UserSearchAPI(
        paramMap.getOrElse("first_name", ""), paramMap.getOrElse("last_name", ""),
        paramMap.getOrElse("email", ""), paramMap.getOrElse("mobile", ""))
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "privacy" :: mobileOrUUID :: Nil Get _ => {
      val uuid = PuluoSession.userUUID
      val api = new UserSettingAPI(uuid)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "setting" :: "update" :: Nil Post _ => {
      val optionalParams = Seq("auto_add_friend", "allow_stranger_view_timeline", "allow_searchedl")
      val paramMap = PuluoResponseFactory.createParamMap(optionalParams)
      logger.info("param map for users/setting/update:\n" + paramMap.mkString("\n"))
      val api = new UserSettingUpdateAPI(paramMap)
      api.execute()
      PuluoResponseFactory.createJSONResponse(api)
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