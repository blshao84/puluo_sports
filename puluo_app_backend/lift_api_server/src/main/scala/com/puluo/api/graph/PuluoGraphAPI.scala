package com.puluo.api.graph
import net.liftweb.http.rest.RestHelper
import net.liftweb.http.JsonResponse
import com.puluo.api.util.PuluoSession
import com.puluo.api.util.SessionInfo
import net.liftweb.http.js.JsExp.strToJsExp
import com.puluo.api.util.PuluoResponseFactory
import com.puluo.api.social.ListFriendsAPI
import com.puluo.api.util.PuluoAPIUtil
import net.liftweb.common.Loggable
import com.puluo.api.util.ErrorResponseResult
import com.puluo.session.PuluoSessionManager
import com.puluo.api.social.RequestFriendAPI
import com.puluo.api.social.DeleteFriendAPI
import com.puluo.api.social.DenyFriendAPI
import com.puluo.api.social.ApproveFriendAPI
import com.puluo.api.social.ListFriendRequestsAPI
import com.puluo.api.social.ListBlacklistAPI
import com.puluo.api.social.UpdateBlacklistAPI

object PuluoGraphAPI extends RestHelper with PuluoAPIUtil with Loggable {
  serve {
    case "users" :: "blacklist" :: "get" :: mobileOrUUID :: Nil Post _ => {
      val api = new ListBlacklistAPI(mobileOrUUID)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }

    case "users" :: "blacklist" :: "ban" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doBanUser)
    }
    case "users" :: "blacklist" :: "free" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doFreeUser)
    }
    case "users" :: "friends" :: mobileOrUUID :: Nil Post _ => {
      val param = PuluoResponseFactory.createParamMap(Seq("limit","offset"))
      val limit = getIntParam(param, "limit", 0)
      val offset = getIntParam(param, "offset", 0)
      val api = new ListFriendsAPI(mobileOrUUID,limit,offset)
      safeRun(api)
      PuluoResponseFactory.createJSONResponse(api)
    }

    case "users" :: "friends" :: "request" :: "send" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doRequestFriend)
    }
    case "users" :: "friends" :: "request" :: "delete" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doDeleteFriend)
    }
    case "users" :: "friends" :: "request" :: "deny" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doDenyFriend)
    }
    case "users" :: "friends" :: "request" :: "approve" :: Nil Post _ => {
      callWithAuthParam(Map(
        "user_uuid" -> ErrorResponseResult(15).copy(message = "user_uuid")))(doApproveFriend)
    }
    case "users" :: "friends" :: "request" :: "get" :: Nil Post _ => doGetFriendRequest
  }

  private def doGetFriendRequest = {
    val params = PuluoResponseFactory.createParamMap(Seq("token","limit","offset"))
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val uuid = session.userUUID()
    val limit = getIntParam(params, "limit", 0)
    val offset = getIntParam(params, "offset", 0)
    val api = new ListFriendRequestsAPI(uuid,limit,offset)
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api)
  }

  private def doUserGraphAPI(params: Map[String, String], apiType: String, apiName: String) = {
    val toUserUUID = params("user_uuid")
    val token = params("token")
    val session = PuluoSessionManager.getSession(token)
    val fromUserUUID = session.userUUID()
    logger.info(String.format(
      "create %sFriendAPI, token=%s,session=%s,to_user=%s,from_user=%s",
      apiType, token, session.sessionID(), toUserUUID, fromUserUUID))
    val (api, code) = (apiName, apiType) match {
      case ("friend", "Request") => (new RequestFriendAPI(toUserUUID, fromUserUUID), 201)
      case ("friend", "Delete") => (new DeleteFriendAPI(toUserUUID, fromUserUUID), 200)
      case ("friend", "Deny") => (new DenyFriendAPI(fromUserUUID, toUserUUID), 200)
      case ("friend", "Approve") => (new ApproveFriendAPI(fromUserUUID, toUserUUID), 200)
      case ("blacklist", "ban") => (new UpdateBlacklistAPI(fromUserUUID, toUserUUID, "ban"), 200)
      case ("blacklist", "free") => (new UpdateBlacklistAPI(fromUserUUID, toUserUUID, "free"), 200)
    }
    safeRun(api)
    PuluoResponseFactory.createJSONResponse(api, code)
  }

  private def doBanUser(params: Map[String, String]) = doUserGraphAPI(params, "ban", "blacklist")
  private def doFreeUser(params: Map[String, String]) = doUserGraphAPI(params, "free", "blacklist")
  private def doRequestFriend(params: Map[String, String]) = doUserGraphAPI(params, "Request", "friend")
  private def doDeleteFriend(params: Map[String, String]) = doUserGraphAPI(params, "Delete", "friend")
  private def doDenyFriend(params: Map[String, String]) = doUserGraphAPI(params, "Deny", "friend")
  private def doApproveFriend(params: Map[String, String]) = doUserGraphAPI(params, "Approve", "friend")
}