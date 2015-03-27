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
import com.puluo.session.PuluoSessionManager

object PuluoUserAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "status" :: Nil Get _ => {
      PuluoResponseFactory.createDummyJSONResponse("{\"login\":true}")
    }
    case "users" :: mobileOrUUID :: Nil Post _ => {
      val api = new UserProfileAPI(mobileOrUUID)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }
    case "users" :: "update" :: Nil Post _ => callWithAuthParam()(doUserUpdate)
    case "users" :: "search" :: Nil Post _ => doUserSearch

    case "users" :: "privacy" :: mobileOrUUID :: Nil Post _ => doUserSetting

    case "users" :: "setting" :: "update" :: Nil Post _ => callWithAuthParam()(doSettingUpdate)
  }

  private def doUserSetting = {
    val token = PuluoResponseFactory.createParamMap(Seq("token")).values.head
    val uuid = PuluoSessionManager.getUserUUID(token)
    val api = new UserSettingAPI(uuid)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doSettingUpdate(params: Map[String, String]) = {
    val optionalParams = Seq("auto_add_friend", "allow_stranger_view_timeline", "allow_searchedl")
    val optionalParamsMap = PuluoResponseFactory.createParamMap(optionalParams)
    logger.info("param map for users/setting/update:\n" + optionalParamsMap.mkString("\n"))
    val token = params("token")
    val uuid = PuluoSessionManager.getUserUUID(token)
    val api = new UserSettingUpdateAPI(uuid, optionalParamsMap)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
  private def doUserUpdate(params: Map[String, String]) = {
    val optionalParams = Seq(
      "first_name", "last_name", "thumbnail", "large_image", "saying",
      "email", "sex", "birthday", "country", "state", "city", "zip")
    val optionalParamsMap = PuluoResponseFactory.createParamMap(optionalParams)
    logger.info("param map for users/update:\n" + optionalParams.mkString("\n"))
    val token = params("token")
    val uuid = PuluoSessionManager.getUserUUID(token)
    val api = new UserProfileUpdateAPI(
      uuid, optionalParamsMap.getOrElse("first_name", ""), optionalParamsMap.getOrElse("last_name", ""),
      optionalParamsMap.getOrElse("thumbnail", ""), optionalParamsMap.getOrElse("large_image", ""),
      optionalParamsMap.getOrElse("saying", ""), optionalParamsMap.getOrElse("email", ""),
      optionalParamsMap.getOrElse("sex", ""), optionalParamsMap.getOrElse("birthday", ""),
      optionalParamsMap.getOrElse("country", ""), optionalParamsMap.getOrElse("state", ""),
      optionalParamsMap.getOrElse("city", ""), optionalParamsMap.getOrElse("zip", ""))
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doUserSearch = {
    val optionalParams = Seq("first_name", "last_name", "email", "mobile")
    val optionalParamsMap = PuluoResponseFactory.createParamMap(optionalParams)
    logger.info("param map for users/search:\n" + optionalParamsMap.mkString("\n"))
    val api = new UserSearchAPI(
      optionalParamsMap.getOrElse("first_name", ""), optionalParamsMap.getOrElse("last_name", ""),
      optionalParamsMap.getOrElse("email", ""), optionalParamsMap.getOrElse("mobile", ""))
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }
}